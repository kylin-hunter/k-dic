package com.kylinhunter.nlp.dic.core.dictionary;

import java.lang.reflect.Constructor;

import com.kylinhunter.plat.commons.exception.inner.InitException;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public class DictionaryCreator {

    public static <T> Dictionary<T> create(DictionaryType dictionaryType, int skipMaxLen) {
        try {
            Constructor<? extends Dictionary> constructor = dictionaryType.getClazz().getConstructor(int.class);
            return constructor.newInstance(skipMaxLen);

        } catch (Exception e) {
            throw new InitException("init dictionary error", e);
        }

    }
}
