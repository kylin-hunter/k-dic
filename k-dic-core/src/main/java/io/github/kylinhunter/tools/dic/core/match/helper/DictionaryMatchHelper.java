package io.github.kylinhunter.tools.dic.core.match.helper;

import java.util.List;

import com.google.common.collect.Lists;

import io.github.kylinhunter.tools.dic.core.dictionary.Dictionary;
import io.github.kylinhunter.tools.dic.core.dictionary.Dictionary.MatchContext;
import io.github.kylinhunter.tools.dic.core.dictionary.bean.WordNode;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.MatchLevel;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchFrag;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;
import io.github.kylinhunter.tools.dic.core.trie.TrieNode;

/**
 * @author BiJi'an
 * @description
 * @date 2022/12/4
 **/
public class DictionaryMatchHelper {

    /**
     * @param matchFrag matchFrag
     * @param wordNode  wordNode
     * @return io.github.kylinhunter.toolsdic.core.match.bean.MatchResult
     * @title toMatchResult
     * @description
     * @author BiJi'an
     * @date 2022-01-27 02:44
     */
    public static <T extends WordNode> MatchResult<T> toMatchResult(MatchFrag<T> matchFrag, T wordNode) {
        MatchLevel matchLevel = matchFrag.getLevel();
        MatchResult<T> matchResult = new MatchResult<>(matchLevel);
        matchResult.setHitWord(matchFrag.getHitWord());
        matchResult.setStart(matchFrag.getStart());
        matchResult.setEnd(matchFrag.getEnd());
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
    public static <T extends WordNode> int getDefaultMaxScanLen(Dictionary<T> dictionary, FindLevel findLevel) {
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
     * @return java.util.List<io.github.kylinhunter.toolsdic.core.match.bean.MatchFrag>
     * @title add
     * @description
     * @author BiJi'an
     * @date 2022-01-28 03:07
     */
    public static <T extends WordNode> List<MatchFrag<T>> add(List<MatchFrag<T>> datas, String text, int start, int len,
                                                              MatchContext<T> matchContext) {

        if (datas == null) {
            datas = Lists.newArrayList();
        }

        datas.add(new MatchFrag<>(text, start, len, matchContext));
        return datas;

    }

    /**
     * @param datas    datas
     * @param text     text
     * @param start    start
     * @param len      len
     * @param distNode distNode
     * @return java.util.List<io.github.kylinhunter.tools.dic.core.match.bean.MatchFrag>
     * @title add
     * @description
     * @author BiJi'an
     * @date 2022-12-04 02:44
     */
    public static <T extends WordNode> List<MatchFrag<T>> add(List<MatchFrag<T>> datas, String text, int start, int len,
                                                              TrieNode<T> distNode) {

        if (datas == null) {
            datas = Lists.newArrayList();
        }
        datas.add(new MatchFrag<>(text, start, len, distNode));
        return datas;

    }
}
