package com.kylinhunter.nlp.dic.core.match.imp;

import java.util.ArrayList;
import java.util.List;

import com.kylinhunter.nlp.dic.core.analyzer.bean.Word;
import com.kylinhunter.nlp.dic.core.analyzer.bean.Words;
import com.kylinhunter.nlp.dic.core.dictionary.Dictionary;
import com.kylinhunter.nlp.dic.core.dictionary.bean.MatchContext;
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;
import com.kylinhunter.nlp.dic.core.dictionary.constant.MatchLevel;
import com.kylinhunter.nlp.dic.core.dictionary.group.DictionaryGroup;
import com.kylinhunter.nlp.dic.core.dictionary.trie.TrieNode;
import com.kylinhunter.nlp.dic.core.match.DicMatch;
import com.kylinhunter.nlp.dic.core.match.bean.DictionarySearch;
import com.kylinhunter.nlp.dic.core.match.bean.MatchResult;
import com.kylinhunter.nlp.dic.core.match.bean.MatchWordNode;
import com.kylinhunter.nlp.dic.core.match.component.DicMatchHelper;
import com.kylinhunter.nlp.dic.core.match.component.DicSkipper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DicMatchFull extends AbstractDicMatch implements DicMatch {

    public DicMatchFull(DictionaryGroup<MatchWordNode> dictionaryGroup) {
        super(dictionaryGroup);
    }

    public List<MatchResult> process(String text, FindLevel findLevel, Dictionary<MatchWordNode> dictionary) {
        if (dictionary.size() <= 0 || text == null || text.length() < 1) {
            return null;
        }
        char[] textChars = this.dicSkipper.replaceSkipChar(text, findLevel);

        List<DictionarySearch> dictionarySearches = null; // 存储临时结果
        MatchContext<MatchWordNode> matchContext = new MatchContext(findLevel);

        int curLen = textChars.length;
        int start = 0;
        int curScanLen;
        int scanMax;
        int defaultMaxScanLen = DicMatchHelper.getDefaultMaxScanLen(dictionary, findLevel);
        int matchNum;
        int matchMinLen;
        while (curLen > 0) {
            curScanLen = 1;
            scanMax = curLen < defaultMaxScanLen ? curLen : defaultMaxScanLen;
            matchNum = 0;
            matchMinLen = Integer.MAX_VALUE;
            while (true) {
                if (textChars[start] == DicSkipper.SPECIAL_CHAR) {
                    break; // 加快速度 跳过 非法字符
                }
                dictionary.match(textChars, start, curScanLen, matchContext);
                TrieNode<MatchWordNode> node = matchContext.node;
                if (node != null && node.isTerminal()) {
                    matchNum++;
                    if (curScanLen < matchMinLen) {
                        matchMinLen = curScanLen;
                    }
                    // System.out.println("find node:" + node.getValues().get(0).getKeyword());
                    dictionarySearches = DicMatchHelper.add(dictionarySearches, text, start, curScanLen, matchContext);
                }
                if (matchNum > 0) {
                    if (matchNum == 1) { // 找到一个词头
                        if (curScanLen > matchMinLen + 1) { // 最多在扫描两个字符,找不到退出
                            break;
                        }
                    } else { // 找到多个词头相同的词头，结束
                        break;
                    }
                }
                if (curScanLen == scanMax) {
                    break;
                }
                curScanLen++;
            }
            if (matchNum > 0) {
                start += matchMinLen;
                curLen -= matchMinLen;
            } else {
                start += 1;
                curLen -= 1;
            }
        }
        return merge(text, dictionarySearches);
    }

    public List<MatchResult> merge(String oriText, List<DictionarySearch> dictionarySearches) {
        if (dictionarySearches != null && dictionarySearches.size() > 0) {
            List<MatchResult> matchResults = new ArrayList<>();
            Words oriSplitWords = analyzer.analyze(oriText);
            for (DictionarySearch dictionarySearch : dictionarySearches) {
                //                System.out.println("dictionarySearch:" + dictionarySearch);

                TrieNode<MatchWordNode> node = dictionarySearch.getNode();

                List<MatchWordNode> wordNodes = dictionarySearch.getWordNodes();
                if (wordNodes != null && wordNodes.size() > 0) {
                    for (MatchWordNode wordNode : wordNodes) { // 某一个词可能对应多个分类

                        MatchResult matchResult =
                                tryGetFindResultFull(oriText, dictionarySearch, wordNode, oriSplitWords);

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

    /**
     * @param oriText
     * @param dictionarySearch
     * @param wordNode
     * @param oriTextSplitWords
     * @return com.kylinhunter.nlp.dic.core.match.bean.MatchResult
     * @throws
     * @title tryGetFindResultFull
     * @description
     * @author BiJi'an
     * @updateTime 2022-04-27 02:44
     */
    private MatchResult tryGetFindResultFull(String oriText, DictionarySearch dictionarySearch, MatchWordNode wordNode,
                                             Words oriTextSplitWords) {

        MatchLevel matchLevel = dictionarySearch.getLevel();
        if (matchLevel == MatchLevel.NONE) {
            return null;
        }
        MatchResult matchResult = DicMatchHelper.toMatchResult(dictionarySearch, wordNode);

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
