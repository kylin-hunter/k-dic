package io.github.kylinhunter.tools.dic.core.match.component;

import java.util.List;

import com.google.common.collect.Lists;

import io.github.kylinhunter.tools.dic.core.dictionary.Dictionary;
import io.github.kylinhunter.tools.dic.core.dictionary.bean.MatchContext;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.MatchLevel;
import io.github.kylinhunter.tools.dic.core.match.bean.DictionarySearch;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;
import io.github.kylinhunter.tools.dic.core.match.bean.WordNode;
import io.github.kylinhunter.tools.dic.core.trie.TrieNode;

/**
 * @author BiJi'an
 * @description
 * @create 2022-01-28 01:28
 **/
public class DicMatchHelper {

    /**
     * @param dictionarySearch
     * @param wordNode
     * @return io.github.kylinhunter.toolsdic.core.match.bean.MatchResult
     * @throws
     * @title toMatchResult
     * @description
     * @author BiJi'an
     * @date 2022-01-27 02:44
     */
    public static MatchResult toMatchResult(DictionarySearch dictionarySearch, WordNode wordNode) {
        MatchLevel matchLevel = dictionarySearch.getLevel();
        MatchResult matchResult = new MatchResult(wordNode.getType(), wordNode.getClassId(), matchLevel.getCode());
        matchResult.setHitWord(dictionarySearch.getHitWord());
        matchResult.setStart(dictionarySearch.getStart());
        matchResult.setEnd(dictionarySearch.getEnd());
        matchResult.setWordNode(wordNode);
        return matchResult;
    }

    /**
     * @param dictionary
     * @param findLevel
     * @return int
     * @throws
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
     * @param datas
     * @param text
     * @param start
     * @param len
     * @param matchContext
     * @return java.util.List<io.github.kylinhunter.toolsdic.core.match.bean.DictionarySearch>
     * @throws
     * @title add
     * @description
     * @author BiJi'an
     * @date 2022-01-28 03:07
     */
    public static List<DictionarySearch> add(List<DictionarySearch> datas, String text, int start, int len,
                                             MatchContext matchContext) {

        if (datas == null) {
            datas = Lists.newArrayList();
        }
        datas.add(new DictionarySearch(text, start, len, matchContext));
        return datas;

    }

    public static List<DictionarySearch> add(List<DictionarySearch> datas, String text, int start, int len,
                                             TrieNode<WordNode> distNode) {

        if (datas == null) {
            datas = Lists.newArrayList();
        }
        datas.add(new DictionarySearch(text, start, len, distNode));
        return datas;

    }
}
