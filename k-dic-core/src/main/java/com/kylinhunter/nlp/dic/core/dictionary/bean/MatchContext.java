package com.kylinhunter.nlp.dic.core.dictionary.bean;

import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;
import com.kylinhunter.nlp.dic.core.dictionary.trie.TrieNode;

import lombok.NoArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01
 **/
@NoArgsConstructor
public class MatchContext<T> {

    /**
     * @see FindLevel
     */
    public int findLevel = 1;

    /**
     * @see com.kylinhunter.nlp.dic.core.dictionary.constant.MatchLevel
     */
    public int matchLevel = 0;

    public TrieNode<T> node;

    public MatchContext(FindLevel findLevel) {
        this.findLevel = findLevel.getCode();
    }
}
