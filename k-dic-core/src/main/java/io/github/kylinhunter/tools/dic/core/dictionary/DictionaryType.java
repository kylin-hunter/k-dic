package io.github.kylinhunter.tools.dic.core.dictionary;

import io.github.kylinhunter.commons.component.CT;
import io.github.kylinhunter.tools.dic.core.dictionary.imp.DictionaryTrie;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
@SuppressWarnings("ALL")
@RequiredArgsConstructor
public enum DictionaryType implements CT<Dictionary> {
    DEFAULT(DictionaryTrie.class),
    TRIE(DictionaryTrie.class);
    @Getter
    public final Class<? extends Dictionary> clazz;

}