package com.kylinhunter.nlp.dic.core.dictionary.bean;

import com.kylinhunter.nlp.dic.core.dictionary.trie.TrieNode;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2022-01-01
 **/
public class FindContext<T> {

    /**
     * @see com.kylinhunter.nlp.dic.core.dictionary.constant.MatchLevel
     */
    public int matchLevel = 0;

    /**
     * @see com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel
     */
    public int findLevel = 1;

    public int maxSkip = 2;

    public TrieNode<T> node;

}
