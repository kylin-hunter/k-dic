package com.kylinhunter.nlp.dic.core.dictionary;

import com.kylinhunter.nlp.dic.commons.service.SimpleService;
import com.kylinhunter.nlp.dic.commons.service.SimpleServiceFactory;
import com.kylinhunter.nlp.dic.core.dictionary.imp.DictionaryTrie;

import lombok.Getter;
import lombok.Setter;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022/1/1
 **/
public enum DictionaryType implements SimpleService<Dictionary> {
    DEFAULT(DictionaryTrie.class),
    TRIE(DictionaryTrie.class);

    @Getter
    @Setter
    Class<? extends Dictionary> clazz;
    @Setter
    @Getter
    int serviceId;

    DictionaryType(Class<? extends Dictionary> clazz) {
        this.serviceId = SimpleServiceFactory.nextServiceId();
        this.clazz = clazz;

    }

}
