package com.kylinhunter.nlp.dic.core.dic;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.kylinhunter.nlp.dic.core.dic.constants.DicType;
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;
import com.kylinhunter.nlp.dic.core.match.TestCaseDicMatchFull;
import com.kylinhunter.nlp.dic.core.match.TestDicMatchHelper;
import com.kylinhunter.nlp.dic.core.match.bean.MatchResult;

class DicTypSensitiveTest {

    static Dic dic;

    @BeforeAll
    static void init() {
        dic = DicManager.get(DicType.SENSITIVE);
    }

    @Test
    void processHigh() {
        FindLevel findLevel = FindLevel.HIGH;
        List<MatchResult> matchResults = dic.match(TestCaseDicMatchFull.text, findLevel);
        Assertions.assertArrayEquals(TestCaseDicMatchFull.get(findLevel),
                TestDicMatchHelper.toStringArr(TestCaseDicMatchFull.text, findLevel, matchResults));

    }

    @Test
    void processHighMiddle() {

        FindLevel findLevel = FindLevel.HIGH_MIDDLE;
        List<MatchResult> matchResults = dic.match(TestCaseDicMatchFull.text, findLevel);
        Assertions.assertArrayEquals(TestCaseDicMatchFull.get(findLevel),
                TestDicMatchHelper.toStringArr(TestCaseDicMatchFull.text, findLevel, matchResults));
    }

    @Test
    void processHighMiddleLow() {

        FindLevel findLevel = FindLevel.HIGH_MIDDLE_LOW;
        List<MatchResult> matchResults = dic.match(TestCaseDicMatchFull.text, findLevel);
        Assertions.assertArrayEquals(TestCaseDicMatchFull.get(findLevel),
                TestDicMatchHelper.toStringArr(TestCaseDicMatchFull.text, findLevel, matchResults));

    }

    @Test
    void processNull() {
        String text = "hello";
        List<MatchResult> matchResults = dic.match(text, FindLevel.HIGH_MIDDLE_LOW);
        Assertions.assertNull(TestDicMatchHelper.toStringArr(text, FindLevel.HIGH_MIDDLE_LOW, matchResults));

    }
}