package com.kylinhunter.nlp.dic.core.dictionary.group.bean;


import com.kylinhunter.nlp.dic.commons.util.EnumUtil;

import lombok.Getter;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2022-01-01 15:10
 **/
public enum HitMode implements EnumUtil.EnumCode {
    HIGH(0, "HIGH"),
    MIDDLE(1, "HIGH_MIDDLE"),
    LOW(2, "HIGH_MIDDLE_LOW");
    @Getter
    private int code;
    private String name;

    HitMode(int code, String name) {
        this.code = code;
        this.name = name;
    }

}
