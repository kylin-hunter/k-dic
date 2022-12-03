package io.github.kylinhunter.tools.dic.app;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.tools.dic.app.constants.DicType;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;

class DictionaryAPPTypSensitiveTest {

    static DicAPP dicAPP;

    @BeforeAll
    static void init() {
        dicAPP = DicManager.get(DicType.SENSITIVE);
    }

    @Test
    void processHigh() {
        FindLevel findLevel = FindLevel.HIGH;
        List<MatchResult> matchResults = dicAPP.match(TestCaseDicMatchFull.text, findLevel);
        Assertions.assertArrayEquals(TestCaseDicMatchFull.get(findLevel),
                TestDicMatchHelper.toStringArr(TestCaseDicMatchFull.text, findLevel, matchResults));

    }

    @Test
    void processHighMiddle() {

        FindLevel findLevel = FindLevel.HIGH_MIDDLE;
        List<MatchResult> matchResults = dicAPP.match(TestCaseDicMatchFull.text, findLevel);
        Assertions.assertArrayEquals(TestCaseDicMatchFull.get(findLevel),
                TestDicMatchHelper.toStringArr(TestCaseDicMatchFull.text, findLevel, matchResults));
    }

    @Test
    void processHighMiddleLow() {

        FindLevel findLevel = FindLevel.HIGH_MIDDLE_LOW;
        List<MatchResult> matchResults = dicAPP.match(TestCaseDicMatchFull.text, findLevel);
        Assertions.assertArrayEquals(TestCaseDicMatchFull.get(findLevel),
                TestDicMatchHelper.toStringArr(TestCaseDicMatchFull.text, findLevel, matchResults));

    }

    @Test
    void processNull() {
        String text = "hello";
        List<MatchResult> matchResults = dicAPP.match(text, FindLevel.HIGH_MIDDLE_LOW);
        Assertions.assertNull(TestDicMatchHelper.toStringArr(text, FindLevel.HIGH_MIDDLE_LOW, matchResults));

    }
}