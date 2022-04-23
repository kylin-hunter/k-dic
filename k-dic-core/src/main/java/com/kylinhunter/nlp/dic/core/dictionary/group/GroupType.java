package com.kylinhunter.nlp.dic.core.dictionary.group;

import com.kylinhunter.nlp.dic.commons.util.EnumUtil;

import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public enum GroupType implements EnumUtil.EnumCode {
    HIGH(1),
    MIDDLE(2),
    LOW(3),
    HIGH_MIDDLE_LOW(4),
    MIDDLE_LOW(5);
    @Getter
    private final int code;

    GroupType(int code) {
        this.code = code;
    }

}
