package io.github.kylinhunter.tools.dic.core.match;

import java.lang.reflect.Constructor;

import io.github.kylinhunter.tools.dic.core.dictionary.group.DictionaryGroup;
import io.github.kylinhunter.tools.dic.core.match.bean.WordNode;

import io.github.kylinhunter.commons.exception.embed.InitException;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public class DicMatchCreator {

    public static DicMatch create(DicMatchType dicMatchType, DictionaryGroup<WordNode> dictionaryGroup) {
        try {
            Constructor<? extends DicMatch> constructor = dicMatchType.getClazz().getConstructor(DictionaryGroup.class);
            return constructor.newInstance(dictionaryGroup);

        } catch (Exception e) {
            throw new InitException("init dictionary error", e);
        }

    }
}
