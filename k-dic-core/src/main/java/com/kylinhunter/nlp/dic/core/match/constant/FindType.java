package com.kylinhunter.nlp.dic.core.match.constant;


import com.kylinhunter.nlp.dic.commons.util.EnumUtil;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 15:10
 **/
public enum FindType implements EnumUtil.EnumCode {
    FIRST_PREFIX(1),
    PREFIX(2),
    FULL(3);
    @Getter
    private final int code;

    FindType(int code) {
        this.code = code;
    }

}
