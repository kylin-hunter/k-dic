package com.kylinhunter.nlp.dic.core.dictionary;

import java.util.List;

import com.kylinhunter.nlp.dic.core.dictionary.bean.FindContext;
import com.kylinhunter.nlp.dic.core.dictionary.trie.TrieNode;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2022/1/1
 **/
public interface Dictionary<T> {

    int getMaxLength();

    int size();

    boolean put(String word, T t);

    boolean contains(String item);

    List<T> getValues(String item);

    T getValue(String item);

    TrieNode<T> getRootNode(char character);

    /**
     * judge a part of text if a word or not
     *
     * @param text   the text for judge
     * @param start  the start postion of text
     * @param length the length from start position
     * @return
     */
    void find(char[] text, int start, int length, FindContext<T> dicResult);

    void find(String text, FindContext<T> dicResult);

}
