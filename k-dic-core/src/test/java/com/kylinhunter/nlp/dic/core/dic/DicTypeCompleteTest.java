package com.kylinhunter.nlp.dic.core.dic;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.kylinhunter.nlp.dic.core.dic.constants.DicType;
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;
import com.kylinhunter.nlp.dic.core.match.TestCaseDicMatchPrefix;
import com.kylinhunter.nlp.dic.core.match.TestDicMatchHelper;
import com.kylinhunter.nlp.dic.core.match.bean.MatchResult;

class DicTypeCompleteTest {

    static Dic dic;

    @BeforeAll
    static void init() {
        dic = DicManager.get(DicType.COMPLETE);
    }

    @Test
    void processF1Char() {

        String text = "北";
        FindLevel findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        text = "北**";

        findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        text = "北**1**";
        findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

    }

    @Test
    void processF2Char() {

        String text = "北京";

        FindLevel findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        text = "北**京";

        findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        text = "北**1**京";

        findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

    }

    @Test
    void processBack3Char() {

        String text = "我爱北京欢";
        FindLevel findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        text = "我爱北京**欢";
        findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        text = "我爱北京**1**欢";
        findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dic.match(text, findLevel)));

    }

    @Test
    void processNull() {
        String text = "我其实很爱";
        FindLevel findLevel = FindLevel.HIGH;
        List<MatchResult> matchResults = dic.match(text, findLevel);
        List<String> resultString = TestDicMatchHelper.toString(text, findLevel, matchResults);
        Assertions.assertNull(resultString);

        text = "我其实很**爱";
        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.toString(text, findLevel, matchResults);
        Assertions.assertNull(resultString);

        text = "我其实很**1**爱";
        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.toString(text, findLevel, matchResults);
        Assertions.assertNull(resultString);

    }
}