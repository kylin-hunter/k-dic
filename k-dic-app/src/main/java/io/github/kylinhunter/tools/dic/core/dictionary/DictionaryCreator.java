package io.github.kylinhunter.tools.dic.core.dictionary;

import java.lang.reflect.Constructor;

import io.github.kylinhunter.commons.exception.embed.InitException;

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
