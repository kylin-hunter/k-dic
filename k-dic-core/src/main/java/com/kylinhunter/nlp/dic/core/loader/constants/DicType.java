package com.kylinhunter.nlp.dic.core.loader.constants;

import lombok.Getter;

/**
 * @author BiJi'an
 * @description DicType
 * @date 2022-01-01 15:10
 **/
public enum DicType {
    SENSITIVE(1, "/config/dic/dic-sensitive.xlsx", true),
    PINYIN(2, "/config/dic/pinyin.txt", true),
    COMPLETE(3, "/config/dic/complete.txt", true),
    ASSOCIATE(4, "/config/dic/associate.txt", true),
    SYNONYM(5, "/config/dic/synonym.txt", true),
    PROFESSIONAL(6, "/config/dic/professional.txt", true),
    CORRECTION(7, "/config/dic/correction.txt", true);

    private final int code;

    @Getter
    private final String path;
    @Getter
    private final boolean secondaryWordsMatch;

    DicType(int code, String path, boolean secondaryWordsMatch) {
        this.code = code;
        this.path = path;
        this.secondaryWordsMatch = secondaryWordsMatch;
    }

    public int getCode() {
        return code;
    }
}
