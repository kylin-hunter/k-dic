package io.github.kylinhunter.tools.dic.core.match.imp;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.group.DictionaryGroup;
import io.github.kylinhunter.tools.dic.core.dictionary.group.bean.HitMode;
import io.github.kylinhunter.tools.dic.core.match.DicMatch;
import io.github.kylinhunter.tools.dic.core.match.DicMatchCreator;
import io.github.kylinhunter.tools.dic.core.match.DicMatchType;
import io.github.kylinhunter.tools.dic.core.match.TestCaseDicMatchFull;
import io.github.kylinhunter.tools.dic.core.match.TestDicMatchHelper;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;
import io.github.kylinhunter.tools.dic.core.match.component.WordNodeConvertor;
import io.github.kylinhunter.tools.dic.words.analyzer.WordAnalyzer;
import io.github.kylinhunter.tools.dic.words.analyzer.WordAnalyzerType;

class DicMatchFullTest {

    static WordAnalyzer analyzer = CF.get(WordAnalyzerType.DEFAULT.clazz);
    static DicMatch dicMatch;

    @BeforeAll
    static void init() {

        DictionaryGroup dictionaryGroup = null;

        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "北京", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "北京海淀", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "河北", "廊坊,张家口", "", analyzer, 10));

        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.MIDDLE, "乌鲁木齐", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.MIDDLE, "呼和浩特", "新疆", "", analyzer, 10));

        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.LOW, "上海", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.LOW, "山西", "大同府,阎王寨", "", analyzer, 10));

        dicMatch = DicMatchCreator.create(DicMatchType.FULL, dictionaryGroup);

    }

    @Test
    void processHigh() {
        FindLevel findLevel = FindLevel.HIGH;
        List<MatchResult> matchResults = dicMatch.match(TestCaseDicMatchFull.text, findLevel);
        Assertions.assertArrayEquals(TestCaseDicMatchFull.get(findLevel),
                TestDicMatchHelper.toStringArr(TestCaseDicMatchFull.text, findLevel, matchResults));

    }

    @Test
    void processHighMiddle() {

        FindLevel findLevel = FindLevel.HIGH_MIDDLE;
        List<MatchResult> matchResults = dicMatch.match(TestCaseDicMatchFull.text, findLevel);
        Assertions.assertArrayEquals(TestCaseDicMatchFull.get(findLevel),
                TestDicMatchHelper.toStringArr(TestCaseDicMatchFull.text, findLevel, matchResults));
    }

    @Test
    void processHighMiddleLow() {

        FindLevel findLevel = FindLevel.HIGH_MIDDLE_LOW;
        List<MatchResult> matchResults = dicMatch.match(TestCaseDicMatchFull.text, findLevel);
        Assertions.assertArrayEquals(TestCaseDicMatchFull.get(findLevel),
                TestDicMatchHelper.toStringArr(TestCaseDicMatchFull.text, findLevel, matchResults));

    }

    @Test
    void processNull() {
        String text = "hello";
        List<MatchResult> matchResults = dicMatch.match(text, FindLevel.HIGH_MIDDLE_LOW);
        Assertions.assertNull(TestDicMatchHelper.toStringArr(text, FindLevel.HIGH_MIDDLE_LOW, matchResults));

    }

}