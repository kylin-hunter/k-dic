package com.kylinhunter.nlp.dic.core.dic.constants;

import com.kylinhunter.nlp.dic.core.match.DicMatchType;

import lombok.Getter;

/**
 * @author BiJi'an
 * @description DicType
 * @date 2022-01-01 15:10
 **/
public enum DicType {
    SENSITIVE(1, DicMatchType.FULL, "/config/dic/k-dic-sensitive.xlsx"),
    PINYIN(2, DicMatchType.FULL, "/config/dic/pinyin.txt"),
    COMPLETE(3, DicMatchType.FULL, "/config/dic/complete.txt"),
    ASSOCIATE(4, DicMatchType.FULL, "/config/dic/associate.txt"),
    SYNONYM(5, DicMatchType.FULL, "/config/dic/synonym.txt"),
    PROFESSIONAL(6, DicMatchType.FULL, "/config/dic/professional.txt"),
    CORRECTION(7, DicMatchType.FULL, "/config/dic/correction.txt");

    private final int code;

    @Getter
    private DicMatchType dicMatchType;
    @Getter
    private final String path;

    DicType(int code, DicMatchType dicMatchType, String path) {
        this.dicMatchType = dicMatchType;
        this.code = code;
        this.path = path;
    }

    public int getCode() {
        return code;
    }
}
