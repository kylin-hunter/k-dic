package com.kylinhunter.nlp.dic.core.dictionary.constant;


import com.kylinhunter.nlp.dic.commons.util.EnumUtil;

import lombok.Getter;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2022-01-01 15:10
 **/
public enum FindLevel implements EnumUtil.EnumCode {
    HIGH(1, "HIGH"),
    HIGH_MIDDLE(2, "HIGH_MIDDLE"),
    HIGH_MIDDLE_LOW(3, "HIGH_MIDDLE_LOW");
    @Getter
    private int code;
    private String name;

    FindLevel(int code, String name) {
        this.code = code;
        this.name = name;
    }

}
