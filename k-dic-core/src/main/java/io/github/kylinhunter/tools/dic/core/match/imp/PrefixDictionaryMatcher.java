package io.github.kylinhunter.tools.dic.core.match.imp;

import java.util.ArrayList;
import java.util.List;

import io.github.kylinhunter.tools.dic.core.dictionary.Dictionary;
import io.github.kylinhunter.tools.dic.core.dictionary.Dictionary.MatchContext;
import io.github.kylinhunter.tools.dic.core.dictionary.DictionaryGroup;
import io.github.kylinhunter.tools.dic.core.dictionary.bean.WordNode;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.helper.DictionarySkipper;
import io.github.kylinhunter.tools.dic.core.match.DictionaryMatcher;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchSplit;
import io.github.kylinhunter.tools.dic.core.match.helper.DictionaryMatchHelper;
import io.github.kylinhunter.tools.dic.core.trie.TrieHelper;
import io.github.kylinhunter.tools.dic.core.trie.TrieNode;
import io.github.kylinhunter.tools.dic.words.analyzer.WordAnalyzerType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrefixDictionaryMatcher extends AbstractDictionaryMatcher implements DictionaryMatcher {

    public PrefixDictionaryMatcher(DictionaryGroup dictionaryGroup, WordAnalyzerType wordAnalyzerType) {
        super(dictionaryGroup, wordAnalyzerType);
    }

    @SuppressWarnings("CommentedOutCode")
    public List<MatchResult> process(String text, FindLevel findLevel, Dictionary<WordNode> dictionary) {
        if (dictionary.size() <= 0 || text == null || text.length() < 1) {
            return null;
        }
        char[] textChars = this.dictionarySkipper.replaceSkipChar(text, findLevel);
        List<MatchSplit> matchSplits = null; // tmp save
        MatchContext<WordNode> matchContext = new MatchContext<>(findLevel);
        int curLen = textChars.length;
        int end = textChars.length;
        int defaultMaxScanLen = DictionaryMatchHelper.getDefaultMaxScanLen(dictionary, findLevel);
        int scanMax;
        int curScanLen;

        while (curLen > 0) {
            if (textChars[end - 1] != DictionarySkipper.SPECIAL_CHAR) {
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
                            matchSplits =
                                    DictionaryMatchHelper.add(matchSplits, text, end - curScanLen, curScanLen,
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

        return merge(text, matchSplits);
    }

    public List<MatchResult> merge(String oriText, List<MatchSplit> matchSplits) {
        if (matchSplits != null && matchSplits.size() > 0) {
            List<MatchResult> matchResults = new ArrayList<>();
            for (MatchSplit matchSplit : matchSplits) {
                //                System.out.println("matchSplit:" + matchSplit);

                TrieNode<WordNode> node = matchSplit.getNode();

                List<WordNode> wordNodes = node.getValues();
                if (wordNodes != null && wordNodes.size() > 0) {
                    for (WordNode wordNode : wordNodes) { // 某一个词可能对应多个分类

                        MatchResult matchResult = tryGetMatchResult(oriText, matchSplit, wordNode);
                        matchResults.add(matchResult);
                    }
                }

            }
            return matchResults;

        }
        return null;

    }

    /**
     * @param oriText    oriText
     * @param matchSplit matchSplit
     * @param wordNode   wordNode
     * @return io.github.kylinhunter.toolsdic.core.match.bean.MatchResult
     * @title tryGetMatchResult
     * @description
     * @author BiJi'an
     * @date 2022-01-27 02:44
     */
    private MatchResult tryGetMatchResult(String oriText, MatchSplit matchSplit, WordNode wordNode) {

        MatchResult matchResult = DictionaryMatchHelper.toMatchResult(matchSplit, wordNode);
        matchResult.setHitWord(oriText.substring(0, matchResult.getStart()) + wordNode.getKeyword());
        return matchResult;

    }

}
