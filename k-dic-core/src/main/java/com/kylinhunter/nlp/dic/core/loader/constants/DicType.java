package com.kylinhunter.nlp.dic.core.loader.constants;

import com.kylinhunter.nlp.dic.core.match.DicMatchType;

import lombok.Getter;

/**
 * @author BiJi'an
 * @description DicType
 * @date 2022-01-01 15:10
 **/
public enum DicType {
    SENSITIVE(1, DicMatchType.DEFAULT, "/config/dic/dic-sensitive.xlsx", true),
    PINYIN(2, DicMatchType.DEFAULT, "/config/dic/pinyin.txt", true),
    COMPLETE(3, DicMatchType.DEFAULT, "/config/dic/complete.txt", true),
    ASSOCIATE(4, DicMatchType.DEFAULT, "/config/dic/associate.txt", true),
    SYNONYM(5, DicMatchType.DEFAULT, "/config/dic/synonym.txt", true),
    PROFESSIONAL(6, DicMatchType.DEFAULT, "/config/dic/professional.txt", true),
    CORRECTION(7, DicMatchType.DEFAULT, "/config/dic/correction.txt", true);

    private final int code;

    private DicMatchType dicMatchType;
    @Getter
    private final String path;
    @Getter
    private final boolean assistMatch;

    DicType(int code, DicMatchType dicMatchType, String path, boolean assistMatch) {
        this.dicMatchType = dicMatchType;
        this.code = code;
        this.path = path;
        this.assistMatch = assistMatch;
    }

    public int getCode() {
        return code;
    }
}
