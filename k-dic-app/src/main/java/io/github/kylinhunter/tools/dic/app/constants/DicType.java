package io.github.kylinhunter.tools.dic.app.constants;

import io.github.kylinhunter.tools.dic.app.excel.DicDataReader;
import io.github.kylinhunter.tools.dic.app.excel.DicDataReaderForComplete;
import io.github.kylinhunter.tools.dic.app.excel.DicDataReaderForPinyin;
import io.github.kylinhunter.tools.dic.app.excel.DicDataReaderForSensitive;
import io.github.kylinhunter.tools.dic.core.match.MatcherType;

import lombok.Getter;

/**
 * @author BiJi'an
 * @description DicType
 * @date 2022-01-01 15:10
 **/
public enum DicType {
    SENSITIVE(1, MatcherType.FULL, "/k-dic-default/k-dic-sensitive-default.xlsx", DicDataReaderForSensitive.class),
    COMPLETE(2, MatcherType.PREFIX, "/k-dic-default/k-dic-complete-default.xlsx", DicDataReaderForComplete.class),
    PINYIN(3, MatcherType.FULL, "/config/dic/pinyin.txt", DicDataReaderForPinyin.class),
    ASSOCIATE(4, MatcherType.FULL, "/config/dic/associate.txt", null),
    SYNONYM(5, MatcherType.FULL, "/config/dic/synonym.txt", null),
    PROFESSIONAL(6, MatcherType.FULL, "/config/dic/professional.txt", null),
    CORRECTION(7, MatcherType.FULL, "/config/dic/correction.txt", null);

    private final int code;

    @Getter
    private MatcherType matcherType;
    @Getter
    private final String path;
    @Getter
    public Class<? extends DicDataReader> clazz;

    DicType(int code, MatcherType matcherType, String path, Class<? extends DicDataReader> clazz) {
        this.matcherType = matcherType;
        this.code = code;
        this.path = path;
        this.clazz = clazz;
    }

    public int getCode() {
        return code;
    }
}
