package io.github.kylinhunter.tools.dic.core.match;

import io.github.kylinhunter.commons.component.CT;
import io.github.kylinhunter.tools.dic.core.match.imp.FullDictionaryMatcher;
import io.github.kylinhunter.tools.dic.core.match.imp.PrefixDictionaryMatcher;
import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description all hitWord analyzers
 * @date 2022-01-08 00:08
 **/
public enum MatcherType implements CT<DictionaryMatcher> {
    FULL(FullDictionaryMatcher.class),
    PREFIX(PrefixDictionaryMatcher.class);
    @Getter
    @Setter
    Class<? extends DictionaryMatcher> clazz;

    MatcherType(Class<? extends DictionaryMatcher> clazz) {
        this.clazz = clazz;

    }
}
