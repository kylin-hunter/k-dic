package com.kylinhunter.nlp.dic.core.match.component;

import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.kylinhunter.nlp.dic.core.dictionary.Dictionary;
import com.kylinhunter.nlp.dic.core.dictionary.bean.MatchContext;
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;
import com.kylinhunter.nlp.dic.core.dictionary.constant.MatchLevel;
import com.kylinhunter.nlp.dic.core.dictionary.trie.TrieNode;
import com.kylinhunter.nlp.dic.core.match.bean.DictionarySearch;
import com.kylinhunter.nlp.dic.core.match.bean.MatchResult;
import com.kylinhunter.nlp.dic.core.match.bean.WordNode;

/**
 * @author BiJi'an
 * @description
 * @create 2022-01-28 01:28
 **/
public class DicMatchHelper {

    /**
     * @param dictionarySearch
     * @param wordNode
     * @return com.kylinhunter.nlp.dic.core.match.bean.MatchResult
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
        int maxLength = dictionary.getMaxLength();
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
     * @return java.util.List<com.kylinhunter.nlp.dic.core.match.bean.DictionarySearch>
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
