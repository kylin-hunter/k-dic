package com.kylinhunter.nlp.dic.core.match;

import java.lang.reflect.Constructor;

import com.kylinhunter.nlp.dic.commons.exception.internal.KInitException;
import com.kylinhunter.nlp.dic.core.dictionary.group.DictionaryGroup;
import com.kylinhunter.nlp.dic.core.match.bean.MatchWordNode;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public class DicMatchCreator {

    public static DicMatch create(DicMatchType dicMatchType, DictionaryGroup<MatchWordNode> dictionaryGroup) {
        try {
            Constructor<? extends DicMatch> constructor = dicMatchType.getClazz().getConstructor(DictionaryGroup.class);
            return constructor.newInstance(dictionaryGroup);

        } catch (Exception e) {
            throw new KInitException("init dictionary error", e);
        }

    }
}
