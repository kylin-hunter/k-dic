package io.github.kylinhunter.tools.dic.core.dic.constants;

import io.github.kylinhunter.tools.dic.core.dic.excel.DicDataReader;
import io.github.kylinhunter.tools.dic.core.dic.excel.DicDataReaderForComplete;
import io.github.kylinhunter.tools.dic.core.dic.excel.DicDataReaderForPinyin;
import io.github.kylinhunter.tools.dic.core.dic.excel.DicDataReaderForSensitive;
import io.github.kylinhunter.tools.dic.core.match.DicMatchType;

import lombok.Getter;

/**
 * @author BiJi'an
 * @description DicType
 * @date 2022-01-01 15:10
 **/
public enum DicType {
    SENSITIVE(1, DicMatchType.FULL, "/k-dic-default/k-dic-sensitive-default.xlsx", DicDataReaderForSensitive.class),
    COMPLETE(2, DicMatchType.PREFIX, "/k-dic-default/k-dic-complete-default.xlsx", DicDataReaderForComplete.class),
    PINYIN(3, DicMatchType.FULL, "/config/dic/pinyin.txt", DicDataReaderForPinyin.class),
    ASSOCIATE(4, DicMatchType.FULL, "/config/dic/associate.txt", null),
    SYNONYM(5, DicMatchType.FULL, "/config/dic/synonym.txt", null),
    PROFESSIONAL(6, DicMatchType.FULL, "/config/dic/professional.txt", null),
    CORRECTION(7, DicMatchType.FULL, "/config/dic/correction.txt", null);

    private final int code;

    @Getter
    private DicMatchType dicMatchType;
    @Getter
    private final String path;
    @Getter
    public Class<? extends DicDataReader> clazz;

    DicType(int code, DicMatchType dicMatchType, String path, Class<? extends DicDataReader> clazz) {
        this.dicMatchType = dicMatchType;
        this.code = code;
        this.path = path;
        this.clazz = clazz;
    }

    public int getCode() {
        return code;
    }
}
