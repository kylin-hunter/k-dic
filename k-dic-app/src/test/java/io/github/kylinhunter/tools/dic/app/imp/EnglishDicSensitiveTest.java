package io.github.kylinhunter.tools.dic.app.imp;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.tools.dic.app.imp.DicSensitive.SensitiveWord;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.HitMode;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;

class EnglishDicSensitiveTest {
    static DicSensitive dicSensitive = new DicSensitive();

    @BeforeAll
    static void init() {

        dicSensitive.add(HitMode.HIGH, "kylin");
        dicSensitive.add(HitMode.HIGH, "kylinOS");
        dicSensitive.add(HitMode.HIGH, "teacher", new String[] {"male", "tall"});

        dicSensitive.add(HitMode.MIDDLE, "fox");
        dicSensitive.add(HitMode.MIDDLE, "dog", new String[] {"cat"});

        dicSensitive.add(HitMode.LOW, "movie");
        dicSensitive.add(HitMode.LOW, "star", new String[] {"jackie", "bruce"});

    }

    @Test
    void processHigh() {
        FindLevel findLevel = FindLevel.HIGH;
        List<MatchResult<SensitiveWord>> matchResults = dicSensitive.match(
                io.github.kylinhunter.tools.dic.app.imp.EnglishTestCaseDicMatchFull.text, findLevel);
        matchResults.forEach(System.out::println);
        Assertions.assertArrayEquals(io.github.kylinhunter.tools.dic.app.imp.EnglishTestCaseDicMatchFull.get(findLevel),
                TestDicMatchHelper
                        .toStringArr(io.github.kylinhunter.tools.dic.app.imp.EnglishTestCaseDicMatchFull.text, findLevel,
                                matchResults));

    }

    @Test
    void processHighMiddle() {

        FindLevel findLevel = FindLevel.HIGH_MIDDLE;
        List<MatchResult<SensitiveWord>> matchResults =
                dicSensitive.match(io.github.kylinhunter.tools.dic.app.imp.EnglishTestCaseDicMatchFull.text, findLevel);
        matchResults.forEach(System.out::println);
        Assertions.assertArrayEquals(io.github.kylinhunter.tools.dic.app.imp.EnglishTestCaseDicMatchFull.get(findLevel),
                TestDicMatchHelper
                        .toStringArr(io.github.kylinhunter.tools.dic.app.imp.EnglishTestCaseDicMatchFull.text, findLevel,
                                matchResults));
    }

    @Test
    void processHighMiddleLow() {

        FindLevel findLevel = FindLevel.HIGH_MIDDLE_LOW;
        List<MatchResult<SensitiveWord>> matchResults =
                dicSensitive.match(io.github.kylinhunter.tools.dic.app.imp.EnglishTestCaseDicMatchFull.text, findLevel);
        matchResults.forEach(System.out::println);
        Assertions.assertArrayEquals(io.github.kylinhunter.tools.dic.app.imp.EnglishTestCaseDicMatchFull.get(findLevel),
                TestDicMatchHelper
                        .toStringArr(io.github.kylinhunter.tools.dic.app.imp.EnglishTestCaseDicMatchFull.text, findLevel,
                                matchResults));

    }

}