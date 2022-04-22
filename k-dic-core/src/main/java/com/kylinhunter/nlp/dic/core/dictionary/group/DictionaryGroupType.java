package com.kylinhunter.nlp.dic.core.dictionary.group;

import com.kylinhunter.nlp.dic.commons.util.EnumUtil;

import lombok.Getter;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022/1/1
 **/
public enum DictionaryGroupType implements EnumUtil.EnumCode {
    HIGH(0, "HIGH"),
    MIDDLE(1, "MIDDLE"),
    LOW(2, "LOW"),
    HIGH_MIDDLE_LOW(3, "HIGH_MIDDLE_LOW"),
    MIDDLE_LOW(4, "MIDDLE_LOW");
    @Getter
    private int code;
    private String name;

    DictionaryGroupType(int code, String name) {
        this.code = code;
        this.name = name;
    }

}
