package io.github.kylinhunter.tools.dic.core.dictionary.constant;



import io.github.kylinhunter.commons.lang.EnumUtils;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 15:10
 **/
public enum MatchLevel implements EnumUtils.EnumCode {
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
