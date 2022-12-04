package io.github.kylinhunter.tools.dic.core.match;

import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.reflect.BeanCreator;
import io.github.kylinhunter.tools.dic.core.dictionary.DictionaryGroup;
import io.github.kylinhunter.tools.dic.words.analyzer.WordAnalyzerType;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public class DictionaryMatcherFactory {

    /**
     * @param dictionaryMatcherType dictionaryMatcherType
     * @param dictionaryGroup       dictionaryGroup
     * @return io.github.kylinhunter.tools.dic.core.match.DictionaryMatcher
     * @title create
     * @description
     * @author BiJi'an
     * @date 2022-12-04 02:58
     */
    public static DictionaryMatcher create(DictionaryMatcherType dictionaryMatcherType, DictionaryGroup dictionaryGroup,
                                           WordAnalyzerType wordAnalyzerType) {
        try {
            return BeanCreator.createBean(dictionaryMatcherType.getClazz(), new Class[] {
                    DictionaryGroup.class, WordAnalyzerType.class,
            }, new Object[] {dictionaryGroup, wordAnalyzerType});

        } catch (Exception e) {
            throw new InitException("init dictionary error", e);
        }

    }

    /**
     * @param dictionaryMatcherType dictionaryMatcherType
     * @param dictionaryGroup       dictionaryGroup
     * @return io.github.kylinhunter.tools.dic.core.match.DictionaryMatcher
     * @title create
     * @description
     * @author BiJi'an
     * @date 2022-12-04 03:06
     */
    public static DictionaryMatcher create(DictionaryMatcherType dictionaryMatcherType, DictionaryGroup dictionaryGroup) {

        return create(dictionaryMatcherType, dictionaryGroup, WordAnalyzerType.DEFAULT);
    }
}
