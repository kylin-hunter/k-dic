package io.github.kylinhunter.tools.dic.app.imp;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.tools.dic.app.bean.DicWordSensitive;
import io.github.kylinhunter.tools.dic.app.bean.DicWords;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.HitMode;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;

class DicSensitiveTest {
    static DicSensitive dicSensitive = new DicSensitive();

    @BeforeAll
    static void init() {

        dicSensitive.addWord(DicWords.createSensitive(HitMode.HIGH, "北京"));
        dicSensitive.addWord(DicWords.createSensitive(HitMode.HIGH, "北京海淀"));
        dicSensitive.addWord(DicWords.createSensitive(HitMode.HIGH, "河北", new String[] {"廊坊", "张家口"}));

        dicSensitive.addWord(DicWords.createSensitive(HitMode.MIDDLE, "乌鲁木齐"));
        dicSensitive.addWord(DicWords.createSensitive(HitMode.MIDDLE, "呼和浩特", new String[] {"新疆"}));

        dicSensitive.addWord(DicWords.createSensitive(HitMode.LOW, "上海"));
        dicSensitive.addWord(DicWords.createSensitive(HitMode.LOW, "山西", new String[] {"大同府", "阎王寨"}));

    }

    @Test
    void processHigh() {
        FindLevel findLevel = FindLevel.HIGH;
        List<MatchResult<DicWordSensitive>> matchResults = dicSensitive.match(TestCaseDicMatchFull.text, findLevel);

        matchResults.forEach(System.out::println);
        Assertions.assertArrayEquals(TestCaseDicMatchFull.get(findLevel),
                TestDicMatchHelper.toStringArr(TestCaseDicMatchFull.text, findLevel, matchResults));

    }

    @Test
    void processHighMiddle() {

        FindLevel findLevel = FindLevel.HIGH_MIDDLE;
        List<MatchResult<DicWordSensitive>> matchResults = dicSensitive.match(TestCaseDicMatchFull.text, findLevel);
        matchResults.forEach(System.out::println);
        Assertions.assertArrayEquals(TestCaseDicMatchFull.get(findLevel),
                TestDicMatchHelper.toStringArr(TestCaseDicMatchFull.text, findLevel, matchResults));
    }

    @Test
    void processHighMiddleLow() {

        FindLevel findLevel = FindLevel.HIGH_MIDDLE_LOW;
        List<MatchResult<DicWordSensitive>> matchResults = dicSensitive.match(TestCaseDicMatchFull.text, findLevel);
        matchResults.forEach(System.out::println);
        Assertions.assertArrayEquals(TestCaseDicMatchFull.get(findLevel),
                TestDicMatchHelper.toStringArr(TestCaseDicMatchFull.text, findLevel, matchResults));

    }

}