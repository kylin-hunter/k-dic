package com.kylinhunter.nlp.dic.core.loader.constants;

import lombok.Getter;

/**
 * @description: DicType
 * @author: BiJi'an
 * @create: 2022-01-01 15:10
 **/
public enum DicType {
    SENSITIVE(1, "敏感词典", "/config/dic/dic-sensitive.xlsx", true),
    PINYIN(2, "拼音词典", "/config/dic/pinyin.txt", true),
    COMPLETE(3, "补全词典", "/config/dic/complete.txt", true),
    ASSOCIATE(4, "联想词典", "/config/dic/associate.txt", true),
    SYNONYM(5, "同义词典", "/config/dic/synonym.txt", true),
    PROFESSIONAL(6, "专业词典", "/config/dic/professional.txt", true),
    CORRECTION(7, "纠错词典", "/config/dic/correction.txt", true);

    private int code;
    private String name;

    @Getter
    private String path;
    @Getter
    private boolean secondaryWordsMatch = true;

    DicType(int code, String name, String path, boolean secondaryWordsMatch) {
        this.code = code;
        this.name = name;
        this.path = path;
        this.secondaryWordsMatch = secondaryWordsMatch;
    }

    public int getCode() {
        return code;
    }
}
