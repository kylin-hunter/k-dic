package io.github.kylinhunter.tools.dic.core.dic.constants;

import io.github.kylinhunter.commons.util.EnumUtils;

import lombok.Getter;

/**
 * @author bijian
 * @version 1.0
 * @description TODO
 * @date 2022/1/23 0:47
 */
public enum LoadSource implements EnumUtils.EnumCode {
    LOCAL(1),

    DB(2);

    @Getter
    private final int code;

    LoadSource(int code) {
        this.code = code;
    }

}
