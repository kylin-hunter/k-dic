package com.kylinhunter.nlp.dic.core.dic.constants;

import com.kylinhunter.plat.commons.util.EnumUtil;

import lombok.Getter;

/**
 * @author bijian
 * @version 1.0
 * @description TODO
 * @date 2022/1/23 0:47
 */
public enum LoadSource implements EnumUtil.EnumCode {
    LOCAL(1),

    DB(2);

    @Getter
    private final int code;

    LoadSource(int code) {
        this.code = code;
    }

}
