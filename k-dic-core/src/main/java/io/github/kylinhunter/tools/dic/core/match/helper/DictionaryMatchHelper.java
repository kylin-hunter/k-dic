package io.github.kylinhunter.tools.dic.core.match.helper;

import java.util.List;

import com.google.common.collect.Lists;

import io.github.kylinhunter.tools.dic.core.dictionary.Dictionary;
import io.github.kylinhunter.tools.dic.core.dictionary.bean.WordNode;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.MatchLevel;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchSplit;
import io.github.kylinhunter.tools.dic.core.trie.TrieNode;

/**
 * @author BiJi'an
 * @description
 * @date 2022/12/4
 **/
public class DictionaryMatchHelper {

    /**
     * @param matchSplit matchSplit
     * @param wordNode   wordNode
     * @return io.github.kylinhunter.toolsdic.core.match.bean.MatchResult
     * @title toMatchResult
     * @description
     * @author BiJi'an
     * @date 2022-01-27 02:44
     */
    public static MatchResult toMatchResult(MatchSplit matchSplit, WordNode wordNode) {
        MatchLevel matchLevel = matchSplit.getLevel();
        MatchResult matchResult = new MatchResult(wordNode.getType(), wordNode.getClassId(), matchLevel.getCode());
        matchResult.setHitWord(matchSplit.getHitWord());
        matchResult.setStart(matchSplit.getStart());
        matchResult.setEnd(matchSplit.getEnd());
        matchResult.setWordNode(wordNode);
        return matchResult;
    }

    /**
     * @param dictionary dictionary
     * @param findLevel  findLevel
     * @return int
     * @title getDefaultMaxScanLen
     * @description
     * @author BiJi'an
     * @date 2022-01-28 03:00
     */
    public static int getDefaultMaxScanLen(Dictionary<WordNode> dictionary, FindLevel findLevel) {
        int maxLength = dictionary.getWordMaxLen();
        if (findLevel == FindLevel.HIGH) {
            return maxLength;
        } else if (findLevel == FindLevel.HIGH_MIDDLE) {
            return maxLength + dictionary.getSkipMaxLen();
        } else {
            return maxLength + dictionary.getSkipMaxLen() * 2 + 1;
        }

    }

    /**
     * @param datas        datas
     * @param text         text
     * @param start        start
     * @param len          len
     * @param matchContext matchContext
     * @return java.util.List<io.github.kylinhunter.toolsdic.core.match.bean.MatchSplit>
     * @title add
     * @description
     * @author BiJi'an
     * @date 2022-01-28 03:07
     */
    public static List<MatchSplit> add(List<MatchSplit> datas, String text, int start, int len,
                                       Dictionary.MatchContext<WordNode> matchContext) {

        if (datas == null) {
            datas = Lists.newArrayList();
        }
        datas.add(new MatchSplit(text, start, len, matchContext));
        return datas;

    }

    /**
     * @param datas    datas
     * @param text     text
     * @param start    start
     * @param len      len
     * @param distNode distNode
     * @return java.util.List<io.github.kylinhunter.tools.dic.core.match.bean.MatchSplit>
     * @title add
     * @description
     * @author BiJi'an
     * @date 2022-12-04 02:44
     */
    public static List<MatchSplit> add(List<MatchSplit> datas, String text, int start, int len,
                                       TrieNode<WordNode> distNode) {

        if (datas == null) {
            datas = Lists.newArrayList();
        }
        datas.add(new MatchSplit(text, start, len, distNode));
        return datas;

    }
}
