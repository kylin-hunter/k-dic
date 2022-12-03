package io.github.kylinhunter.tools.dic.core.dictionary;

import io.github.kylinhunter.tools.dic.core.dictionary.imp.DictionaryTrie;

import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
@SuppressWarnings("ALL")
public enum DictionaryType {
    DEFAULT(DictionaryTrie.class),
    TRIE(DictionaryTrie.class);

    @Getter
    @Setter
    Class<? extends Dictionary> clazz;

    DictionaryType(Class<? extends Dictionary> clazz) {

        this.clazz = clazz;
    }

}
