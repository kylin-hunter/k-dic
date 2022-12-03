package io.github.kylinhunter.tools.dic.app.constants;

import io.github.kylinhunter.tools.dic.app.excel.DicDataReader;
import io.github.kylinhunter.tools.dic.app.excel.DicDataReaderForComplete;
import io.github.kylinhunter.tools.dic.app.excel.DicDataReaderForPinyin;
import io.github.kylinhunter.tools.dic.app.excel.DicDataReaderForSensitive;
import io.github.kylinhunter.tools.dic.core.match.DictionaryMatcherType;

import lombok.Getter;

/**
 * @author BiJi'an
 * @description DicType
 * @date 2022-01-01 15:10
 **/
public enum DicType {
    SENSITIVE(1, DictionaryMatcherType.FULL, "/k-dic-default/k-dic-sensitive-default.xlsx", DicDataReaderForSensitive.class),
    COMPLETE(2, DictionaryMatcherType.PREFIX, "/k-dic-default/k-dic-complete-default.xlsx", DicDataReaderForComplete.class),
    PINYIN(3, DictionaryMatcherType.FULL, "/config/dic/pinyin.txt", DicDataReaderForPinyin.class),
    ASSOCIATE(4, DictionaryMatcherType.FULL, "/config/dic/associate.txt", null),
    SYNONYM(5, DictionaryMatcherType.FULL, "/config/dic/synonym.txt", null),
    PROFESSIONAL(6, DictionaryMatcherType.FULL, "/config/dic/professional.txt", null),
    CORRECTION(7, DictionaryMatcherType.FULL, "/config/dic/correction.txt", null);

    private final int code;

    @Getter
    private DictionaryMatcherType dictionaryMatcherType;
    @Getter
    private final String path;
    @Getter
    public Class<? extends DicDataReader> clazz;

    DicType(int code, DictionaryMatcherType dictionaryMatcherType, String path, Class<? extends DicDataReader> clazz) {
        this.dictionaryMatcherType = dictionaryMatcherType;
        this.code = code;
        this.path = path;
        this.clazz = clazz;
    }

    public int getCode() {
        return code;
    }
}
