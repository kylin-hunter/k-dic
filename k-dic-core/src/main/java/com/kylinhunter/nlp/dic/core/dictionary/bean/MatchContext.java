package com.kylinhunter.nlp.dic.core.dictionary.bean;

import com.kylinhunter.nlp.dic.core.dictionary.trie.TrieNode;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01
 **/
public class MatchContext<T> {

    /**
     * @see com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel
     */
    public int findLevel = 1;

    /**
     * @see com.kylinhunter.nlp.dic.core.dictionary.constant.MatchLevel
     */
    public int matchLevel = 0;

    public TrieNode<T> node;

    private boolean isTerminal = false;

}
