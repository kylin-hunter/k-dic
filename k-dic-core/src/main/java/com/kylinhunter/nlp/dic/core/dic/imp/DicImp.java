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
import com.kylinhunter.nlp.dic.core.dic.bean.DicSearchResult;
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
        int curScanLen = 0;
        int scanMax = 0;
        MatchContext<WordNode> matchContext = new MatchContext<>();
        List<DicSearchResult> dicSearchResults = null; // 存储临时结果
        matchContext.findLevel = findLevel.getCode();

        int findNum = 0;
        int findMinScanLen = Integer.MAX_VALUE;

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
                        //                        System.out.println("find node:" + node.getValues().get(0)
                        //                        .getKeyword());

                        if (dicSearchResults == null) {
                            dicSearchResults = new ArrayList<>();
                        }
                        DicSearchResult dicSearchResult = new DicSearchResult(
                                oriText.substring(start, start + curScanLen), matchContext.matchLevel,
                                node.getValues(), secondaryWordsMatch);
                        dicSearchResults.add(dicSearchResult);

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

                    } else if (findNum >= 2) { // 找到多个词头相同的词头，结束
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
        return mergeData(oriText, dicSearchResults);
    }

    public List<MatchResult> mergeData(String oriText, List<DicSearchResult> dicSearchResults) {
        if (dicSearchResults != null && dicSearchResults.size() > 0) {
            List<MatchResult> matchResults = new ArrayList<>();
            Words oriSplitWords = analyzer.analyze(oriText);

            for (DicSearchResult dicSearchResult : dicSearchResults) {
                //                System.out.println("dicSearchResult:" + dicSearchResult);
                List<WordNode> wordNodes = dicSearchResult.getWordNodes();
                if (wordNodes != null && wordNodes.size() > 0) {
                    for (WordNode wordNode : wordNodes) { // 某一个词可能对应多个分类
                        MatchResult matchResult =
                                tryGetFindResult(oriText, dicSearchResult, wordNode, oriSplitWords);
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

    private MatchResult tryGetFindResult(String oriText, DicSearchResult dicSearchResult, WordNode wordNode,
                                         Words oriTextSplitWords) {

        MatchLevel matchLevel = dicSearchResult.getLevel();
        MatchResult matchResult = new MatchResult(wordNode.getType(), wordNode.getClassId(), matchLevel.getCode());
        matchResult.setWord(dicSearchResult.getHitWord());
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
        if (dicSearchResult.isSecondaryWordsMatch()) {
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
