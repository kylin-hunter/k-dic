package io.github.kylinhunter.tools.dic.core.match;

import io.github.kylinhunter.tools.dic.core.match.imp.DicMatchFull;
import io.github.kylinhunter.tools.dic.core.match.imp.DicMatchPrefix;

import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description all hitWord analyzers
 * @date 2022-01-08 00:08
 **/
public enum DicMatchType {
    FULL(DicMatchFull.class),
    PREFIX(DicMatchPrefix.class);
    @Getter
    @Setter
    Class<? extends DicMatch> clazz;

    DicMatchType(Class<? extends DicMatch> clazz) {
        this.clazz = clazz;

    }
}
