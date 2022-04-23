package com.kylinhunter.nlp.dic.commons.util;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import com.kylinhunter.nlp.dic.commons.exception.internal.KParamException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description a enum util
 * @date 2022/1/1
 **/
@Slf4j
public class EnumUtil {
    private static final Map<Class<?>, EnumCode[]> ENUM_DATAS = new HashMap<>();

    /**
     * @param enumType enumType
     * @param code     code
     * @return T
     * @title get enum from code
     * @description
     * @author BiJi'an
     * @updateTime 2022/1/1 1:19
     */
    public static <T extends Enum<T>, V extends EnumCode> T fromCode(Class<V> enumType, int code) {
        return fromCode(enumType, code, true);
    }

    /**
     * @param enumType      enumType
     * @param code          code
     * @param throwIfFailed throwIfFailed
     * @return T
     * @title fromCode
     * @description
     * @author BiJi'an
     * @updateTime 2022/1/1 1:23
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>, V extends EnumCode> T fromCode(Class<V> enumType, int code,
                                                                     boolean throwIfFailed) {
        try {
            EnumCode[] enumCodes = ENUM_DATAS.get(enumType);
            if (enumCodes == null) {
                enumCodes = enumType.getEnumConstants();
                if (enumCodes != null) {
                    ENUM_DATAS.put(enumType, enumCodes);
                }
            }
            if (enumCodes != null) {
                for (EnumCode enumCode : enumCodes) {
                    if (enumCode.getCode() == code) {
                        return (T) enumCode;
                    }
                }
            }

        } catch (Exception e) {
            log.error("fromCode  error", e);
        }
        if (throwIfFailed) {
            throw new KParamException("invalid enum code:" + code);
        } else {
            return null;
        }

    }

    /**
     * @param enumType enumType
     * @param codes    codes
     * @return T[]
     * @title fromCode
     * @description
     * @author BiJi'an
     * @updateTime 2022/1/1 1:30
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>, V extends EnumCode> T[] fromCode(Class<V> enumType, int[] codes) {
        if (codes != null && codes.length > 0) {

            T[] ts = (T[]) Array.newInstance(enumType, codes.length);
            for (int i = 0; i < codes.length; i++) {
                T t = fromCode(enumType, codes[i], true);
                ts[i] = t;
            }
            return ts;
        }
        return null;

    }

    public interface EnumCode {
        int getCode();
    }

}
