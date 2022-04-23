package com.kylinhunter.nlp.dic.core.dic.imp;

import java.util.ArrayList;
import java.util.List;

import com.kylinhunter.nlp.dic.commons.util.CollectionUtil;
import com.kylinhunter.nlp.dic.core.analyzer.WordAnalyzer;
import com.kylinhunter.nlp.dic.core.analyzer.bean.Word;
import com.kylinhunter.nlp.dic.core.analyzer.bean.Words;
import com.kylinhunter.nlp.dic.core.dic.Dic;
import com.kylinhunter.nlp.dic.core.dic.component.DicSkipper;
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.WordNode;
import com.kylinhunter.nlp.dic.core.dictionary.Dictionary;
import com.kylinhunter.nlp.dic.core.dictionary.trie.TrieNode;
import com.kylinhunter.nlp.dic.core.dictionary.group.GroupType;
import com.kylinhunter.nlp.dic.core.dictionary.group.DictionaryGroup;
import com.kylinhunter.nlp.dic.core.dic.bean.DictionarySearch;
import com.kylinhunter.nlp.dic.core.dic.bean.MatchResult;
import com.kylinhunter.nlp.dic.core.dic.option.MatchOption;
import com.kylinhunter.nlp.dic.core.dictionary.constant.MatchLevel;
import com.kylinhunter.nlp.dic.core.dictionary.bean.MatchContext;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class DicImp implements Dic {
    private static DicSkipper dicSkipper = DicSkipper.getInstance();
    private final DictionaryGroup dictionaryGroup;
    private final WordAnalyzer analyzer;


    @Override
    public List<MatchResult> match(String inputText, MatchOption matchOption) {
        FindLevel findLevel = matchOption.getFindLevel();
        boolean secondaryWordsMatch = dictionaryGroup.isSecondaryWordsMatch();
        char[] text = inputText.toCharArray();
        if (findLevel == FindLevel.HIGH) {
            return process(inputText, text, FindLevel.HIGH, secondaryWordsMatch,
                    dictionaryGroup.get(GroupType.HIGH_MIDDLE_LOW));

        } else if (findLevel == FindLevel.HIGH_MIDDLE) {
            List<MatchResult> resultHigh = process(inputText, text, FindLevel.HIGH, secondaryWordsMatch,
                    dictionaryGroup.get(GroupType.HIGH));
            List<MatchResult> resultMiddle =
                    process(inputText, text, FindLevel.HIGH_MIDDLE, secondaryWordsMatch,
                            dictionaryGroup.get(GroupType.MIDDLE_LOW));
            return CollectionUtil.merge(resultHigh, resultMiddle);

        } else if (findLevel == FindLevel.HIGH_MIDDLE_LOW) {
            List<MatchResult> resultHigh = process(inputText, text, FindLevel.HIGH, secondaryWordsMatch,
                    dictionaryGroup.get(GroupType.HIGH));
            List<MatchResult> resultMiddle = process(inputText, text, FindLevel.HIGH_MIDDLE, secondaryWordsMatch,
                    dictionaryGroup.get(GroupType.MIDDLE));
            List<MatchResult> resultLow = process(inputText, text, FindLevel.HIGH_MIDDLE_LOW, secondaryWordsMatch,
                    dictionaryGroup.get(GroupType.LOW));
            return CollectionUtil.merge(resultHigh, resultMiddle, resultLow);
        }
        return null;

    }

    public List<MatchResult> process(String oriText, char[] textChars, FindLevel findLevel, boolean secondaryWordsMatch
            , Dictionary<WordNode> dictionary) {
        if (dictionary.size() <= 0) {
            return null;
        }
        int defaultMaxLenth = dictionary.getMaxLength();
        if (findLevel != FindLevel.HIGH) {
            defaultMaxLenth = dictionary.getMaxLength() + 4;
        }

        for (int i = 0; i < textChars.length; i++) {
            if (dicSkipper.isSkip(findLevel, textChars[i])) {
                textChars[i] = DicSkipper.SPECIAL_CHAR;
            }
        }
        int curLen = textChars.length;
        int start = 0;
        int curScanLen;
        int scanMax;
        MatchContext<WordNode> matchContext = new MatchContext<>();
        List<DictionarySearch> dictionarySearches = null; // 存储临时结果
        matchContext.findLevel = findLevel.getCode();

        int findNum;
        int findMinScanLen;

        while (curLen > 0) {

            curScanLen = 1;
            scanMax = curLen < defaultMaxLenth ? curLen : defaultMaxLenth;
            findNum = 0;
            findMinScanLen = Integer.MAX_VALUE;
            while (true) {
                if (textChars[start] != DicSkipper.SPECIAL_CHAR) {
                    dictionary.match(textChars, start, curScanLen, matchContext);
                    TrieNode<WordNode> node = matchContext.node;
                    if (node != null) {
                        findNum++;
                        if (curScanLen < findMinScanLen) {
                            findMinScanLen = curScanLen;
                        }
                        // System.out.println("find node:" + node.getValues().get(0)  .getKeyword());

                        if (dictionarySearches == null) {
                            dictionarySearches = new ArrayList<>();
                        }
                        DictionarySearch dictionarySearch = new DictionarySearch(
                                oriText.substring(start, start + curScanLen), matchContext.matchLevel,
                                node.getValues(), secondaryWordsMatch);
                        dictionarySearches.add(dictionarySearch);

                    }

                } else {
                    break; // 加快速度 跳过 非法字符
                }
                if (curScanLen == scanMax) {
                    break;
                }
                if (findNum > 0) {
                    if (findNum == 1) { // 找到一个词头
                        if (curScanLen > findMinScanLen + 1) { // 最多在扫描两个字符,找不到退出
                            break;
                        }

                    } else { // 找到多个词头相同的词头，结束
                        break;
                    }
                }
                curScanLen++;

            }

            if (findNum > 0) {
                start += findMinScanLen;
                curLen -= findMinScanLen;
            } else {
                start += 1;
                curLen -= 1;
            }

        }
        return mergeData(oriText, dictionarySearches);
    }

    public List<MatchResult> mergeData(String oriText, List<DictionarySearch> dictionarySearches) {
        if (dictionarySearches != null && dictionarySearches.size() > 0) {
            List<MatchResult> matchResults = new ArrayList<>();
            Words oriSplitWords = analyzer.analyze(oriText);

            for (DictionarySearch dictionarySearch : dictionarySearches) {
                //                System.out.println("dicSearchResult:" + dicSearchResult);
                List<WordNode> wordNodes = dictionarySearch.getWordNodes();
                if (wordNodes != null && wordNodes.size() > 0) {
                    for (WordNode wordNode : wordNodes) { // 某一个词可能对应多个分类
                        MatchResult matchResult =
                                tryGetFindResult(oriText, dictionarySearch, wordNode, oriSplitWords);
                        if (matchResult != null) {
                            matchResults.add(matchResult);
                        }
                    }
                }

            }
            return matchResults;

        }
        return null;

    }

    private MatchResult tryGetFindResult(String oriText, DictionarySearch dictionarySearch, WordNode wordNode,
                                         Words oriTextSplitWords) {

        MatchLevel matchLevel = dictionarySearch.getLevel();
        MatchResult matchResult = new MatchResult(wordNode.getType(), wordNode.getClassId(), matchLevel.getCode());
        matchResult.setWord(dictionarySearch.getHitWord());
        matchResult.setWordNode(wordNode);

        if (matchLevel == MatchLevel.HIGH) { // 高度匹配
            Words keywordSplit = wordNode.getKeywordSplit();
            if (keywordSplit != null) { // 包括切词全在才可命中
                for (Word word : keywordSplit.getWords()) {
                    if (!oriTextSplitWords.contains(word.getText())) {
                        return null;
                    }
                }
            }
        }
        if (dictionarySearch.isSecondaryWordsMatch()) {
            if (!wordNode.hasSecondaryWords()) { // 无关联词可以直接返回了
                return matchResult;
            } else { // 有关联词的时候，要求关联词也都要在输入的切词里面

                for (String subWord : wordNode.getSecondaryWords()) {
                    if (oriText.indexOf(subWord) < 0) {
                        return null;
                    }
                }
                matchResult.setSencondaryWords(wordNode.getSecondaryWords());
                return matchResult;

            }
        } else {
            return matchResult;
        }

    }

}
