package io.github.kylinhunter.tools.dic.app.imp;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.tools.dic.app.imp.DicSensitive.SensitiveWord;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.HitMode;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;

class DicSensitiveTest {
    static DicSensitive dicSensitive = new DicSensitive();

    @BeforeAll
    static void init() {

        dicSensitive.add(HitMode.HIGH, "北京");
        dicSensitive.add(HitMode.HIGH, "北京海淀");
        dicSensitive.add(HitMode.HIGH, "河北", new String[] {"廊坊", "张家口"});

        dicSensitive.add(HitMode.MIDDLE, "乌鲁木齐");
        dicSensitive.add(HitMode.MIDDLE, "呼和浩特", new String[] {"新疆"});

        dicSensitive.add(HitMode.LOW, "上海");
        dicSensitive.add(HitMode.LOW, "山西", new String[] {"李小龙", "成龙"});

    }

    @Test
    void processHigh() {
        FindLevel findLevel = FindLevel.HIGH;
        List<MatchResult<SensitiveWord>> matchResults = dicSensitive.match(TestCaseDicMatchFull.text, findLevel);
        matchResults.forEach(System.out::println);
        Assertions.assertArrayEquals(TestCaseDicMatchFull.get(findLevel),
                TestDicMatchHelper.toStringArr(TestCaseDicMatchFull.text, findLevel, matchResults));

    }

    @Test
    void processHighMiddle() {

        FindLevel findLevel = FindLevel.HIGH_MIDDLE;
        List<MatchResult<SensitiveWord>> matchResults =
                dicSensitive.match(TestCaseDicMatchFull.text, findLevel);
        matchResults.forEach(System.out::println);
        Assertions.assertArrayEquals(TestCaseDicMatchFull.get(findLevel),
                TestDicMatchHelper.toStringArr(TestCaseDicMatchFull.text, findLevel, matchResults));
    }

    @Test
    void processHighMiddleLow() {

        FindLevel findLevel = FindLevel.HIGH_MIDDLE_LOW;
        List<MatchResult<SensitiveWord>> matchResults =
                dicSensitive.match(TestCaseDicMatchFull.text, findLevel);
        matchResults.forEach(System.out::println);
        Assertions.assertArrayEquals(TestCaseDicMatchFull.get(findLevel),
                TestDicMatchHelper.toStringArr(TestCaseDicMatchFull.text, findLevel, matchResults));

    }

}