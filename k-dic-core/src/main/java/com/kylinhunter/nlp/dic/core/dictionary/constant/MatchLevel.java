package com.kylinhunter.nlp.dic.core.dictionary.constant;

import com.kylinhunter.nlp.dic.commons.util.EnumUtil;

import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 15:10
 **/
public enum MatchLevel implements EnumUtil.EnumCode {
    NONE(0),

    HIGH(1),

    MIDDLE(2),

    LOW(3);

    @Getter
    private final int code;

    MatchLevel(int code) {
        this.code = code;
    }

}
