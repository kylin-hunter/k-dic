package com.kylinhunter.nlp.dic.commons.util;

import lombok.Getter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnumUtilTest {

    @Test
    void testFromCode1() {
        TestEnum testEnum = EnumUtil.fromCode(TestEnum.class, 1);
        assertEquals(TestEnum.TEST1, testEnum);
        testEnum = EnumUtil.fromCode(TestEnum.class, 2);
        assertEquals(TestEnum.TEST2, testEnum);

    }

    @Test
    void testFromCode2() {
        TestEnum[] testEnum = EnumUtil.fromCode(TestEnum.class, new int[]{1, 2});

        assertEquals(TestEnum.TEST1, testEnum[0]);
        assertEquals(TestEnum.TEST2, testEnum[1]);
    }

    public static enum TestEnum implements EnumUtil.EnumCode {
        TEST1(1, "TEST1"),

        TEST2(2, "TEST2");

        @Getter
        private int code;
        private String name;

        TestEnum(int code, String name) {
            this.code = code;
            this.name = name;
        }

    }
}