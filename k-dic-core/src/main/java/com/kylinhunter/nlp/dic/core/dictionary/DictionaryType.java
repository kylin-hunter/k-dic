package com.kylinhunter.nlp.dic.core.dictionary;

import com.kylinhunter.nlp.dic.commons.service.KService;
import com.kylinhunter.nlp.dic.commons.service.KServices;
import com.kylinhunter.nlp.dic.core.dictionary.imp.DictionaryTrie;

import lombok.Getter;
import lombok.Setter;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022/1/1
 **/
@SuppressWarnings("ALL")
public enum DictionaryType implements KService<Dictionary> {
    DEFAULT(DictionaryTrie.class),
    TRIE(DictionaryTrie.class);

    @Getter
    @Setter
    Class<? extends Dictionary> clazz;
    @Setter
    @Getter
    int serviceId;

    DictionaryType(Class<? extends Dictionary> clazz) {
        this.serviceId = KServices.nextServiceId();
        this.clazz = clazz;

    }

}
