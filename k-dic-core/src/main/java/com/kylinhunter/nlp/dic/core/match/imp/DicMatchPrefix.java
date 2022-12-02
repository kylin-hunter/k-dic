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
        int end = textChars.length;
        int defaultMaxScanLen = DicMatchHelper.getDefaultMaxScanLen(dictionary, findLevel);
        int scanMax;
        int curScanLen;
        int matchNum = 0;

        while (curLen > 0) {
            if (textChars[end - 1] != DicSkipper.SPECIAL_CHAR) {
                scanMax = curLen < defaultMaxScanLen ? curLen : defaultMaxScanLen;
                curScanLen = scanMax;
                while (curScanLen > 0) {
                    //System.out.println(findLevel + ",end:" + end + ",curLen:" + curLen + ",curScanLen:"
                    //       + curScanLen + ",try:" + (end - curScanLen) + ":" + end + ">" + text
                    //       .substring(end - curScanLen, end));
                    dictionary.match(textChars, end - curScanLen, curScanLen, matchContext);
                    TrieNode<WordNode> node = matchContext.node;
                    if (node != null) {
                        matchNum++;
                        //System.out.println("find node" + node.getCharacter());
                        List<TrieNode<WordNode>> distNodes = TrieHelper.prefix(node, 10);
                        for (TrieNode<WordNode> distNode : distNodes) {
                            dictionarySearches =
                                    DicMatchHelper.add(dictionarySearches, text, end - curScanLen, curScanLen,
                                            distNode);
                            //for (WordNode wordNode : distNode.getValues()) {
                            // System.out.println(wordNode.getKeyword());
                            // }
                        }
                        break;
                    }

                    curScanLen--;
                }
                break;
            } else {

                if (findLevel == FindLevel.HIGH) {
                    break;
                }
                end--;
                curLen -= 1;
            }

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
     * @date 2022-01-27 02:44
     */
    private MatchResult tryGetMatchResult(String oriText, DictionarySearch dictionarySearch, WordNode wordNode) {

        MatchResult matchResult = DicMatchHelper.toMatchResult(dictionarySearch, wordNode);
        matchResult.setHitWord(oriText.substring(0, matchResult.getStart()) + wordNode.getKeyword());
        return matchResult;

    }

}
