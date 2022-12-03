package io.github.kylinhunter.tools.dic.core.dictionary.bean;

import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.trie.TrieNode;

import io.github.kylinhunter.tools.dic.core.dictionary.constant.MatchLevel;
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
     * @see MatchLevel
     */
    public int matchLevel = 0;

    public TrieNode<T> node;

    public MatchContext(FindLevel findLevel) {
        this.findLevel = findLevel.getCode();
    }
}
