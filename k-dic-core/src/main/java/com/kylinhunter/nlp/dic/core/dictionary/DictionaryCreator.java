package com.kylinhunter.nlp.dic.core.dictionary;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.kylinhunter.nlp.dic.commons.exception.internal.KInitException;
import com.kylinhunter.nlp.dic.commons.service.KServices;
import com.kylinhunter.nlp.dic.core.dictionary.bean.MatchContext;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public class DictionaryCreator  {

    public static <T> Dictionary<T> create(DictionaryType dictionaryType, int skipMaxLen) {
        try {
            Constructor<? extends Dictionary> constructor = dictionaryType.getClazz().getConstructor(int.class);
            return constructor.newInstance(skipMaxLen);

        } catch (Exception e) {
            throw new KInitException("init dictionary error", e);
        }

    }
}
