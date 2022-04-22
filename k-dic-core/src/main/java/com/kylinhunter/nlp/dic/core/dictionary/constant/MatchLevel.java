package com.kylinhunter.nlp.dic.core.dictionary.constant;

import com.kylinhunter.nlp.dic.commons.util.EnumUtil;

import lombok.Getter;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022-01-01 15:10
 **/
public enum MatchLevel implements EnumUtil.EnumCode {
    HIGH(1, "HIGH"),
    MIDDLE(2, "MIDDLE"),
    LOW(3, "LOW");
    @Getter
    private int code;
    private String name;

    MatchLevel(int code, String name) {
        this.code = code;
        this.name = name;
    }

}
