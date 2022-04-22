package com.kylinhunter.nlp.dic.core.loader.constants;

import com.kylinhunter.nlp.dic.commons.util.EnumUtil;
import lombok.Getter;

/**
 * @author bijian
 * @version 1.0
 * @description TODO
 * @date 2022/4/23 0:47
 */
public enum LoadSource implements EnumUtil.EnumCode {
    LOCAL(1, "LOCAL"),

    DB(2, "DB");

    @Getter
    private int code;
    private String name;

    LoadSource(int code, String name) {
        this.code = code;
        this.name = name;
    }

}
