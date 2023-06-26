package io.github.kylinhunter.tools.dic.core.dictionary.constant;




import io.github.kylinhunter.commons.lang.EnumUtils;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 15:10
 **/
public enum HitMode implements EnumUtils.EnumCode {
    HIGH(0),
    MIDDLE(1),
    LOW(2);
    @Getter
    private final int code;

    HitMode(int code) {
        this.code = code;
    }

}
