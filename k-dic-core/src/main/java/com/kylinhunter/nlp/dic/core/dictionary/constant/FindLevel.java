package com.kylinhunter.nlp.dic.core.dictionary.constant;



import com.kylinhunter.plat.commons.util.EnumUtil;

import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 15:10
 **/
public enum FindLevel implements EnumUtil.EnumCode {
    HIGH(1),
    HIGH_MIDDLE(2),
    HIGH_MIDDLE_LOW(3);
    @Getter
    private final int code;

    FindLevel(int code) {
        this.code = code;
    }

}
