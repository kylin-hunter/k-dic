package com.kylinhunter.nlp.dic.core.dic.constant;


import com.kylinhunter.nlp.dic.commons.util.EnumUtil;

import lombok.Getter;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2022-01-01 15:10
 **/
public enum FindType implements EnumUtil.EnumCode {
    FIRST_PREFIX(1, "FIRST_PREFIX"),
    PREFIX(1, "PREFIX"),
    FULL(2, "FULL");
    @Getter
    private int code;
    private String name;

    FindType(int code, String name) {
        this.code = code;
        this.name = name;
    }

}
