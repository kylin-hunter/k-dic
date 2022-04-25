package com.kylinhunter.nlp.dic.core.match.imp;

import java.util.ArrayList;
import java.util.List;

import com.kylinhunter.nlp.dic.commons.service.KServices;
import com.kylinhunter.nlp.dic.commons.util.CollectionUtil;
import com.kylinhunter.nlp.dic.core.analyzer.WordAnalyzer;
import com.kylinhunter.nlp.dic.core.analyzer.bean.Word;
import com.kylinhunter.nlp.dic.core.analyzer.bean.Words;
import com.kylinhunter.nlp.dic.core.dictionary.Dictionary;
import com.kylinhunter.nlp.dic.core.dictionary.bean.MatchContext;
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;
import com.kylinhunter.nlp.dic.core.dictionary.constant.MatchLevel;
import com.kylinhunter.nlp.dic.core.dictionary.group.DictionaryGroup;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.WordNode;
import com.kylinhunter.nlp.dic.core.dictionary.trie.TrieNode;
import com.kylinhunter.nlp.dic.core.match.DicMatch;
import com.kylinhunter.nlp.dic.core.match.bean.DictionarySearch;
import com.kylinhunter.nlp.dic.core.match.bean.MatchResult;
import com.kylinhunter.nlp.dic.core.match.bean.MatchWordNode;
import com.kylinhunter.nlp.dic.core.match.component.DicSkipper;
import com.kylinhunter.nlp.dic.core.match.constant.FindType;
import com.kylinhunter.nlp.dic.core.match.option.MatchOption;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DicMatchForwardMinimum implements DicMatch {
    private static DicSkipper dicSkipper = DicSkipper.getInstance();
    private DictionaryGroup<MatchWordNode> dictionaryGroup;
    private WordAnalyzer analyzer;
    private boolean assistMatch;

    public DicMatchForwardMinimum(DictionaryGroup<MatchWordNode> dictionaryGroup) {
        this.dictionaryGroup = dictionaryGroup;
        this.assistMatch = dictionaryGroup.getDicConfig().isAssistMatch();
        this.analyzer = KServices.get(dictionaryGroup.getConfig().getWordAnalyzer());

    }

    @Override
    public List<MatchResult> match(String text, MatchOption matchOption) {
        FindLevel findLevel = matchOption.getFindLevel();
        FindType findType = matchOption.getFindType();
        switch (findLevel) {
            case HIGH: {
                return process(text, findType, FindLevel.HIGH, dictionaryGroup.getHighMiddleLow());
            }
            case HIGH_MIDDLE: {
                List<MatchResult> resultHigh = process(text, findType, FindLevel.HIGH, dictionaryGroup.getHigh());
                List<MatchResult> resultMiddle =
                        process(text, findType, FindLevel.HIGH_MIDDLE, dictionaryGroup.getMiddleLow());
                return CollectionUtil.merge(resultHigh, resultMiddle);
            }
            case HIGH_MIDDLE_LOW: {
                List<MatchResult> resultHigh = process(text, findType, FindLevel.HIGH, dictionaryGroup.getHigh());
                List<MatchResult> resultMiddle =
                        process(text, findType, FindLevel.HIGH_MIDDLE, dictionaryGroup.getMiddle());
                List<MatchResult> resultLow =
                        process(text, findType, FindLevel.HIGH_MIDDLE_LOW, dictionaryGroup.getLow());
                return CollectionUtil.merge(resultHigh, resultMiddle, resultLow);
            }
        }

        return null;

    }

    public List<MatchResult> process(String oriText, FindType findType, FindLevel findLevel,
                                     Dictionary<MatchWordNode> dictionary) {
        if (dictionary.size() <= 0 || oriText == null || oriText.length() < 1) {
            return null;
        }

        char[] textChars = oriText.toCharArray();
        for (int i = 0; i < textChars.length; i++) {
            if (dicSkipper.isSkip(findLevel, textChars[i])) {
                textChars[i] = DicSkipper.SPECIAL_CHAR;
            }
        }

        int curLen = textChars.length;
        int start = 0;
        int curScanLen;
        int scanMax;
        MatchContext<MatchWordNode> matchContext = new MatchContext<>();
        List<DictionarySearch> dictionarySearches = null; // 存储临时结果
        matchContext.findLevel = findLevel.getCode();

        int defaultMaxLenth = dictionary.getMaxLength();
        if (findLevel == FindLevel.HIGH_MIDDLE) {
            defaultMaxLenth = defaultMaxLenth + dictionary.getSkipMaxLen();
        } else if (findLevel == FindLevel.HIGH_MIDDLE_LOW) {
            defaultMaxLenth = defaultMaxLenth + dictionary.getSkipMaxLen() * 2 + 1;

        }

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
                    TrieNode<MatchWordNode> node = matchContext.node;
                    if (node != null && (findType != FindType.FULL || findType == FindType.FULL && node.isTerminal())) {
                        findNum++;
                        if (curScanLen < findMinScanLen) {
                            findMinScanLen = curScanLen;
                        }
                        if (node.getValues() != null) {
                            System.out.println("find node:" + node.getValues().get(0).getKeyword());
                        }

                        if (dictionarySearches == null) {
                            dictionarySearches = new ArrayList<>();
                        }
                        DictionarySearch dictionarySearch = new DictionarySearch(findType,
                                oriText.substring(start, start + curScanLen), matchContext.matchLevel,
                                node);
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
            if (findType == FindType.FIRST_PREFIX) {
                break;

            }

        }
        return mergeData(oriText, dictionarySearches);
    }

    public List<MatchResult> mergeData(String oriText, List<DictionarySearch> dictionarySearches) {
        if (dictionarySearches != null && dictionarySearches.size() > 0) {
            List<MatchResult> matchResults = new ArrayList<>();
            Words oriSplitWords = analyzer.analyze(oriText);

            for (DictionarySearch dictionarySearch : dictionarySearches) {
                //                System.out.println("dictionarySearch:" + dictionarySearch);

                TrieNode<MatchWordNode> node = dictionarySearch.getNode();
                FindType findType = dictionarySearch.getFindType();
                if (findType != FindType.FULL) {
                    List<MatchWordNode> result = new ArrayList<>();
                    this.prefix(node, result, 10);
                    dictionarySearch.setWordNodes(result);
                }

                List<MatchWordNode> wordNodes = dictionarySearch.getWordNodes();
                if (wordNodes != null && wordNodes.size() > 0) {
                    for (MatchWordNode wordNode : wordNodes) { // 某一个词可能对应多个分类

                        MatchResult findResult = null;
                        if (findType == FindType.FULL) {

                            findResult = tryGetFindResultFull(oriText, dictionarySearch, wordNode, oriSplitWords);
                        } else {
                            findResult = tryGetFindResultPrefix(oriText, dictionarySearch, wordNode, oriSplitWords);

                        }

                        if (findResult != null) {
                            matchResults.add(findResult);
                        }
                    }
                }

            }
            return matchResults;

        }
        return null;

    }

    public static <T> void prefix(TrieNode<T> parent, List<T> result, int maxSize) {
        if (result.size() >= maxSize) {
            return;
        }
        if (parent.isTerminal()) {
            result.addAll(parent.getValues());
        }
        TrieNode<T>[] children = parent.getChildren();
        if (children != null) {
            for (TrieNode<T> child : children) {
                prefix(child, result, maxSize);
            }
        }

    }

    private MatchResult tryGetFindResultPrefix(String oriText, DictionarySearch dictionarySearch,
                                               MatchWordNode wordNode,
                                               Words oriTextSplitWords) {
        MatchLevel matchLevel = dictionarySearch.getLevel();
        MatchResult findResult = new MatchResult(wordNode.getType(), wordNode.getClassId(), matchLevel.getCode());
        findResult.setWord(dictionarySearch.getHitWord());
        findResult.setMatchWordNode(wordNode);
        return findResult;

    }

    private MatchResult tryGetFindResultFull(String oriText, DictionarySearch dictionarySearch, MatchWordNode wordNode,
                                             Words oriTextSplitWords) {

        MatchLevel matchLevel = dictionarySearch.getLevel();
        if (matchLevel == MatchLevel.NONE) {
            return null;
        }
        MatchResult matchResult = new MatchResult(wordNode.getType(), wordNode.getClassId(), matchLevel.getCode());
        matchResult.setWord(dictionarySearch.getHitWord());
        matchResult.setMatchWordNode(wordNode);

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
        if (this.assistMatch) {
            if (wordNode.hasSecondaryWords()) { // 有关联词的时候，要求关联词也都要在输入的切词里面
                for (String subWord : wordNode.getAssistWords()) {
                    if (!oriText.contains(subWord)) {
                        return null;
                    }
                }
                matchResult.setAssistWords(wordNode.getAssistWords());
            }
        }
        return matchResult;

    }

}
