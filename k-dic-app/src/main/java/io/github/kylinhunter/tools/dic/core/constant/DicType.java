package io.github.kylinhunter.tools.dic.core.constant;


import io.github.kylinhunter.tools.dic.core.match.MatchType;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description DicType
 * @date 2022-01-01 15:10
 **/
public enum DicType {
    SENSITIVE(1, MatchType.FULL),
    COMPLETE(2, MatchType.PREFIX),
    PINYIN(3, MatchType.FULL),
    ASSOCIATE(4, MatchType.FULL),
    SYNONYM(5, MatchType.FULL),
    PROFESSIONAL(6, MatchType.FULL),
    CORRECTION(7, MatchType.FULL);

    private final int code;

    @Getter
    private MatchType matchType;

    DicType(int code, MatchType matchType) {
        this.matchType = matchType;
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
