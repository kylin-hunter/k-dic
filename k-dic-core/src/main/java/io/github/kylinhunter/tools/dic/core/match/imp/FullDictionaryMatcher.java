package io.github.kylinhunter.tools.dic.core.match.imp;

import java.util.ArrayList;
import java.util.List;

import io.github.kylinhunter.tools.dic.core.dictionary.Dictionary;
import io.github.kylinhunter.tools.dic.core.dictionary.Dictionary.MatchContext;
import io.github.kylinhunter.tools.dic.core.dictionary.DictionaryGroup;
import io.github.kylinhunter.tools.dic.core.dictionary.bean.WordNode;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.MatchLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.helper.DictionarySkipper;
import io.github.kylinhunter.tools.dic.core.match.DictionaryMatcher;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchSplit;
import io.github.kylinhunter.tools.dic.core.match.helper.DictionaryMatchHelper;
import io.github.kylinhunter.tools.dic.core.trie.TrieNode;
import io.github.kylinhunter.tools.dic.words.analyzer.WordAnalyzerType;
import io.github.kylinhunter.tools.dic.words.analyzer.bean.Word;
import io.github.kylinhunter.tools.dic.words.analyzer.bean.Words;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FullDictionaryMatcher extends AbstractDictionaryMatcher implements DictionaryMatcher {

    public FullDictionaryMatcher(DictionaryGroup dictionaryGroup, WordAnalyzerType wordAnalyzerType) {
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
        int start = 0;
        int curScanLen;
        int scanMax;
        int defaultMaxScanLen = DictionaryMatchHelper.getDefaultMaxScanLen(dictionary, findLevel);
        int matchNum;
        int matchMinLen;
        while (curLen > 0) {
            curScanLen = 1;
            scanMax = Math.min(curLen, defaultMaxScanLen);
            matchNum = 0;
            matchMinLen = Integer.MAX_VALUE;
            while (curScanLen <= scanMax) {
                if (textChars[start] == DictionarySkipper.SPECIAL_CHAR) {
                    break; // skip fast
                }
                // System.out.println(findLevel + "start:" + start + ",curScanLen:" + curScanLen + ":"+text.substring
                // (start, start + curScanLen));

                dictionary.match(textChars, start, curScanLen, matchContext);
                TrieNode<WordNode> node = matchContext.node;
                if (node != null && node.isTerminal()) {
                    matchNum++;
                    if (curScanLen < matchMinLen) {
                        matchMinLen = curScanLen;
                    }
                    // System.out.println("find node:" + node.getValues().get(0).getKeyword());
                    matchSplits = DictionaryMatchHelper.add(matchSplits, text, start, curScanLen, matchContext);
                }
                if (matchNum > 0) {
                    if (matchNum == 1) { // find one
                        if (curScanLen > matchMinLen + 1) { // scan two chars
                            break;
                        }
                    } else { // find more than one
                        break;
                    }
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
        return merge(text, matchSplits);
    }

    public List<MatchResult> merge(String oriText, List<MatchSplit> matchSplits) {
        if (matchSplits != null && matchSplits.size() > 0) {
            List<MatchResult> matchResults = new ArrayList<>();
            Words oriSplitWords = analyzer.analyze(oriText);
            for (MatchSplit matchSplit : matchSplits) {
                //                System.out.println("matchSplit:" + matchSplit);

                TrieNode<WordNode> node = matchSplit.getNode();

                List<WordNode> wordNodes = node.getValues();
                if (wordNodes != null && wordNodes.size() > 0) {
                    for (WordNode wordNode : wordNodes) {
                        MatchResult matchResult = tryGetMatchResult(oriText, matchSplit, wordNode, oriSplitWords);
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
     * @param text       text
     * @param matchSplit matchSplit
     * @param wordNode   wordNode
     * @param textWords  textWords
     * @return io.github.kylinhunter.toolsdic.core.match.bean.MatchResult
     * @title tryGetFindResultFull
     * @description
     * @author BiJi'an
     * @date 2022-01-27 02:44
     */
    private MatchResult tryGetMatchResult(String text, MatchSplit matchSplit, WordNode wordNode,
                                          Words textWords) {

        MatchLevel matchLevel = matchSplit.getLevel();
        if (matchLevel == MatchLevel.NONE) {
            return null;
        }
        MatchResult matchResult = DictionaryMatchHelper.toMatchResult(matchSplit, wordNode);

        if (matchLevel == MatchLevel.HIGH) {
            Words keywordSplit = wordNode.getKeywordSplit();
            if (keywordSplit != null) {
                for (Word word : keywordSplit.getWords()) {
                    if (!textWords.contains(word.getText())) {
                        return null;
                    }
                }
            }
        }
        if (this.assistMatchEnabled) {
            if (wordNode.hasAssistWords()) {
                for (String subWord : wordNode.getAssistWords()) {
                    if (!text.contains(subWord)) {
                        return null;
                    }
                }
                matchResult.setAssistWords(wordNode.getAssistWords());
            }
        }
        return matchResult;

    }

}
