package com.kylinhunter.nlp.dic.core.match.imp;

import java.util.ArrayList;
import java.util.List;

import com.kylinhunter.nlp.dic.core.dictionary.Dictionary;
import com.kylinhunter.nlp.dic.core.dictionary.bean.MatchContext;
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;
import com.kylinhunter.nlp.dic.core.dictionary.group.DictionaryGroup;
import com.kylinhunter.nlp.dic.core.dictionary.trie.TrieHelper;
import com.kylinhunter.nlp.dic.core.dictionary.trie.TrieNode;
import com.kylinhunter.nlp.dic.core.match.DicMatch;
import com.kylinhunter.nlp.dic.core.match.bean.DictionarySearch;
import com.kylinhunter.nlp.dic.core.match.bean.MatchResult;
import com.kylinhunter.nlp.dic.core.match.bean.WordNode;
import com.kylinhunter.nlp.dic.core.match.component.DicMatchHelper;
import com.kylinhunter.nlp.dic.core.match.component.DicSkipper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DicMatchPrefix extends AbstractDicMatch implements DicMatch {

    public DicMatchPrefix(DictionaryGroup<WordNode> dictionaryGroup) {
        super(dictionaryGroup);
    }

    public List<MatchResult> process(String text, FindLevel findLevel, Dictionary<WordNode> dictionary) {
        if (dictionary.size() <= 0 || text == null || text.length() < 1) {
            return null;
        }
        char[] textChars = this.dicSkipper.replaceSkipChar(text, findLevel);
        List<DictionarySearch> dictionarySearches = null; // tmp save
        MatchContext<WordNode> matchContext = new MatchContext(findLevel);
        int curLen = textChars.length;
        int start = textChars.length;
        int defaultMaxScanLen = DicMatchHelper.getDefaultMaxScanLen(dictionary, findLevel);
        int scanMax = curLen < defaultMaxScanLen ? curLen : defaultMaxScanLen;
        int curScanLen = scanMax;
        //        System.out.println("start:" + start + ",curLen:" + curLen + ",curScanLen:" + curScanLen);

        while (true) {
            if (textChars[start - 1] == DicSkipper.SPECIAL_CHAR) {
                break; // skip fast
            }
            System.out.println(start - curScanLen + ":" + start);
            System.out.println(text.substring(start - curScanLen, start));
            dictionary.match(textChars, start - curScanLen, curScanLen, matchContext);
            TrieNode<WordNode> node = matchContext.node;
            if (node != null) {
                //                System.out.println("find node" + node.getCharacter());
                List<TrieNode<WordNode>> distNodes = TrieHelper.prefix(node, 10);
                for (TrieNode<WordNode> distNode : distNodes) {
                    dictionarySearches = DicMatchHelper.add(dictionarySearches, text, start - curScanLen, curScanLen,
                            distNode);
                    for (WordNode wordNode : distNode.getValues()) {
                        System.out.println(wordNode.getKeyword());
                    }
                }
                break;
            }
            if (curScanLen <= 1) {
                break;
            }
            curScanLen--;
        }

        return merge(text, dictionarySearches);
    }

    public List<MatchResult> merge(String oriText, List<DictionarySearch> dictionarySearches) {
        if (dictionarySearches != null && dictionarySearches.size() > 0) {
            List<MatchResult> matchResults = new ArrayList<>();
            for (DictionarySearch dictionarySearch : dictionarySearches) {
                //                System.out.println("dictionarySearch:" + dictionarySearch);

                TrieNode<WordNode> node = dictionarySearch.getNode();

                List<WordNode> wordNodes = node.getValues();
                if (wordNodes != null && wordNodes.size() > 0) {
                    for (WordNode wordNode : wordNodes) { // 某一个词可能对应多个分类

                        MatchResult matchResult = tryGetMatchResult(oriText, dictionarySearch, wordNode);

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
     * @return com.kylinhunter.nlp.dic.core.match.bean.MatchResult
     * @throws
     * @title tryGetMatchResult
     * @description
     * @author BiJi'an
     * @updateTime 2022-04-27 02:44
     */
    private MatchResult tryGetMatchResult(String oriText, DictionarySearch dictionarySearch, WordNode wordNode) {

        MatchResult matchResult = DicMatchHelper.toMatchResult(dictionarySearch, wordNode);
        matchResult.setHitWord(oriText.substring(0, matchResult.getStart()) + wordNode.getKeyword());
        return matchResult;

    }

}
