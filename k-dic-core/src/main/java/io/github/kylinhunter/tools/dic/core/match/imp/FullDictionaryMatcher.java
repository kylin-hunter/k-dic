package io.github.kylinhunter.tools.dic.core.match.imp;

import java.util.ArrayList;
import java.util.List;

import io.github.kylinhunter.tools.dic.core.dictionary.Dictionary;
import io.github.kylinhunter.tools.dic.core.dictionary.Dictionary.MatchContext;
import io.github.kylinhunter.tools.dic.core.dictionary.bean.WordNode;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.DicConst;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.MatchLevel;
import io.github.kylinhunter.tools.dic.core.match.DictionaryMatcher;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchFrag;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;
import io.github.kylinhunter.tools.dic.core.match.helper.DictionaryMatchHelper;
import io.github.kylinhunter.tools.dic.core.trie.TrieNode;
import io.github.kylinhunter.tools.dic.words.analyzer.bean.Word;
import io.github.kylinhunter.tools.dic.words.analyzer.bean.Words;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FullDictionaryMatcher extends AbstractDictionaryMatcher implements DictionaryMatcher {



    @SuppressWarnings("CommentedOutCode")
    public List<MatchResult> process(String text, FindLevel findLevel, Dictionary<WordNode> dictionary) {
        if (dictionary.size() <= 0 || text == null || text.length() < 1) {
            return null;
        }
        char[] textChars = this.dictionarySkipper.replaceSkipChar(text, findLevel);

        List<MatchFrag> matchFrags = null; // tmp save
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
                if (textChars[start] == DicConst.SKIP_NULL) {
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
                    matchFrags = DictionaryMatchHelper.add(matchFrags, text, start, curScanLen, matchContext);
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
        return merge(text, matchFrags);
    }

    public List<MatchResult> merge(String oriText, List<MatchFrag> matchFrags) {
        if (matchFrags != null && matchFrags.size() > 0) {
            List<MatchResult> matchResults = new ArrayList<>();
            Words oriSplitWords = wordAnalyzer.analyze(oriText);
            for (MatchFrag matchFrag : matchFrags) {
                //                System.out.println("matchFrag:" + matchFrag);

                TrieNode<WordNode> node = matchFrag.getNode();

                List<WordNode> wordNodes = node.getValues();
                if (wordNodes != null && wordNodes.size() > 0) {
                    for (WordNode wordNode : wordNodes) {
                        MatchResult matchResult = tryGetMatchResult(oriText, matchFrag, wordNode, oriSplitWords);
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
     * @param text      text
     * @param matchFrag matchFrag
     * @param wordNode  wordNode
     * @param textWords textWords
     * @return io.github.kylinhunter.toolsdic.core.match.bean.MatchResult
     * @title tryGetFindResultFull
     * @description
     * @author BiJi'an
     * @date 2022-01-27 02:44
     */
    private MatchResult tryGetMatchResult(String text, MatchFrag matchFrag, WordNode wordNode,
                                          Words textWords) {

        MatchLevel matchLevel = matchFrag.getLevel();
        if (matchLevel == MatchLevel.NONE) {
            return null;
        }
        MatchResult matchResult = DictionaryMatchHelper.toMatchResult(matchFrag, wordNode);

        if (matchLevel == MatchLevel.HIGH) {
            Words keywordSplit = wordNode.getAnalyzedKeywords();
            if (keywordSplit != null) {
                for (Word word : keywordSplit.getWords()) {
                    if (!textWords.contains(word.getText())) {
                        return null;
                    }
                }
            }
        }
        if (this.assistMatchEnabled) {
            if (wordNode.hasAssistedKeywords()) {
                for (String subWord : wordNode.getAssistedKeywords()) {
                    if (!text.contains(subWord)) {
                        return null;
                    }
                }
                matchResult.setAssistWords(wordNode.getAssistedKeywords());
            }
        }
        return matchResult;

    }

}
