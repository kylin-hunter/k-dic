package com.kylinhunter.nlp.dic.core.dictionary.group.bean;



import com.kylinhunter.plat.commons.util.EnumUtil;

import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 15:10
 **/
public enum HitMode implements EnumUtil.EnumCode {
    HIGH(0),
    MIDDLE(1),
    LOW(2);
    @Getter
    private final int code;

    HitMode(int code) {
        this.code = code;
    }

}
