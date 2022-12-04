package io.github.kylinhunter.tools.dic.core.match.imp;

import java.util.ArrayList;
import java.util.List;

import io.github.kylinhunter.tools.dic.core.dictionary.Dictionary;
import io.github.kylinhunter.tools.dic.core.dictionary.Dictionary.MatchContext;
import io.github.kylinhunter.tools.dic.core.dictionary.bean.WordNode;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.DicConst;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.match.DictionaryMatcher;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchFrag;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;
import io.github.kylinhunter.tools.dic.core.match.helper.DictionaryMatchHelper;
import io.github.kylinhunter.tools.dic.core.trie.TrieHelper;
import io.github.kylinhunter.tools.dic.core.trie.TrieNode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrefixDictionaryMatcher<T extends WordNode> extends AbstractDictionaryMatcher<T>
        implements DictionaryMatcher<T> {

    @SuppressWarnings("CommentedOutCode")
    public List<MatchResult<T>> process(String text, FindLevel findLevel, Dictionary<T> dictionary) {
        if (dictionary.size() <= 0 || text == null || text.length() < 1) {
            return null;
        }
        char[] textChars = this.dictionarySkipper.replaceSkipChar(text, findLevel);
        List<MatchFrag<T>> matchFrags = null; // tmp save
        MatchContext<T> matchContext = new MatchContext<>(findLevel);
        int curLen = textChars.length;
        int end = textChars.length;
        int defaultMaxScanLen = DictionaryMatchHelper.getDefaultMaxScanLen(dictionary, findLevel);
        int scanMax;
        int curScanLen;

        while (curLen > 0) {
            if (textChars[end - 1] != DicConst.SKIP_NULL) {
                scanMax = Math.min(curLen, defaultMaxScanLen);
                curScanLen = scanMax;
                while (curScanLen > 0) {
                    //System.out.println(findLevel + ",end:" + end + ",curLen:" + curLen + ",curScanLen:"
                    //       + curScanLen + ",try:" + (end - curScanLen) + ":" + end + ">" + text
                    //       .substring(end - curScanLen, end));
                    dictionary.match(textChars, end - curScanLen, curScanLen, matchContext);
                    TrieNode<T> node = matchContext.node;
                    if (node != null) {
                        //System.out.println("find node" + node.getCharacter());
                        List<TrieNode<T>> distNodes = TrieHelper.prefix(node, 10);
                        for (TrieNode<T> distNode : distNodes) {
                            matchFrags =
                                    DictionaryMatchHelper.add(matchFrags, text, end - curScanLen, curScanLen,
                                            distNode);
                            //for (WordNode wordNode : distNode.getValues()) {
                            // System.out.println(wordNode.getWord());
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

        return merge(text, matchFrags);
    }

    public List<MatchResult<T>> merge(String oriText, List<MatchFrag<T>> matchFrags) {
        if (matchFrags != null && matchFrags.size() > 0) {
            List<MatchResult<T>> matchResults = new ArrayList<>();
            for (MatchFrag<T> matchFrag : matchFrags) {
                //                System.out.println("matchFrag:" + matchFrag);

                TrieNode<T> node = matchFrag.getNode();

                List<T> wordNodes = node.getValues();
                if (wordNodes != null && wordNodes.size() > 0) {
                    for (T wordNode : wordNodes) { // 某一个词可能对应多个分类

                        MatchResult<T> matchResult = tryGetMatchResult(oriText, matchFrag, wordNode);
                        matchResults.add(matchResult);
                    }
                }

            }
            return matchResults;

        }
        return null;

    }

    /**
     * @param oriText   oriText
     * @param matchFrag matchFrag
     * @param wordNode  wordNode
     * @return io.github.kylinhunter.toolsdic.core.match.bean.MatchResult
     * @title tryGetMatchResult
     * @description
     * @author BiJi'an
     * @date 2022-01-27 02:44
     */
    private MatchResult<T> tryGetMatchResult(String oriText, MatchFrag<T> matchFrag, T wordNode) {

        MatchResult<T> matchResult = DictionaryMatchHelper.toMatchResult(matchFrag, wordNode);
        matchResult.setHitWord(oriText.substring(0, matchResult.getStart()) + wordNode.getWord());
        return matchResult;

    }

}
