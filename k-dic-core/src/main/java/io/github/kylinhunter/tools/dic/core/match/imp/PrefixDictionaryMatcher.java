package io.github.kylinhunter.tools.dic.core.match.imp;

import java.util.ArrayList;
import java.util.List;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.tools.dic.core.dictionary.Dictionary;
import io.github.kylinhunter.tools.dic.core.dictionary.Dictionary.MatchContext;
import io.github.kylinhunter.tools.dic.core.dictionary.bean.WordNode;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.DicConst;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.helper.DictionarySkipper;
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
@C
public class PrefixDictionaryMatcher extends AbstractDictionaryMatcher implements DictionaryMatcher {
    public PrefixDictionaryMatcher(DictionarySkipper dictionarySkipper) {
        super(dictionarySkipper);
    }

    @SuppressWarnings("CommentedOutCode")
    public List<MatchResult> process(String text, FindLevel findLevel, Dictionary<WordNode> dictionary) {
        if (dictionary.size() <= 0 || text == null || text.length() < 1) {
            return null;
        }
        char[] textChars = this.dictionarySkipper.replaceSkipChar(text, findLevel);
        List<MatchFrag> matchFrags = null; // tmp save
        MatchContext<WordNode> matchContext = new MatchContext<>(findLevel);
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
                    TrieNode<WordNode> node = matchContext.node;
                    if (node != null) {
                        //System.out.println("find node" + node.getCharacter());
                        List<TrieNode<WordNode>> distNodes = TrieHelper.prefix(node, 10);
                        for (TrieNode<WordNode> distNode : distNodes) {
                            matchFrags =
                                    DictionaryMatchHelper.add(matchFrags, text, end - curScanLen, curScanLen,
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

        return merge(text, matchFrags);
    }

    public List<MatchResult> merge(String oriText, List<MatchFrag> matchFrags) {
        if (matchFrags != null && matchFrags.size() > 0) {
            List<MatchResult> matchResults = new ArrayList<>();
            for (MatchFrag matchFrag : matchFrags) {
                //                System.out.println("matchFrag:" + matchFrag);

                TrieNode<WordNode> node = matchFrag.getNode();

                List<WordNode> wordNodes = node.getValues();
                if (wordNodes != null && wordNodes.size() > 0) {
                    for (WordNode wordNode : wordNodes) { // 某一个词可能对应多个分类

                        MatchResult matchResult = tryGetMatchResult(oriText, matchFrag, wordNode);
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
    private MatchResult tryGetMatchResult(String oriText, MatchFrag matchFrag, WordNode wordNode) {

        MatchResult matchResult = DictionaryMatchHelper.toMatchResult(matchFrag, wordNode);
        matchResult.setHitWord(oriText.substring(0, matchResult.getStart()) + wordNode.getKeyword());
        return matchResult;

    }

}
