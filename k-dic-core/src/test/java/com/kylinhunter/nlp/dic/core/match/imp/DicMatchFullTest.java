package com.kylinhunter.nlp.dic.core.match.imp;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.kylinhunter.nlp.dic.commons.service.KServices;
import com.kylinhunter.nlp.dic.core.analyzer.WordAnalyzer;
import com.kylinhunter.nlp.dic.core.analyzer.WordAnalyzerType;
import com.kylinhunter.nlp.dic.core.config.ConfigHelper;
import com.kylinhunter.nlp.dic.core.config.DicConfig;
import com.kylinhunter.nlp.dic.core.dic.constants.DicType;
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;
import com.kylinhunter.nlp.dic.core.dictionary.group.DictionaryGroup;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.HitMode;
import com.kylinhunter.nlp.dic.core.match.DicMatch;
import com.kylinhunter.nlp.dic.core.match.DicMatchCreator;
import com.kylinhunter.nlp.dic.core.match.DicMatchType;
import com.kylinhunter.nlp.dic.core.match.TestCaseDicMatchFull;
import com.kylinhunter.nlp.dic.core.match.TestDicMatchHelper;
import com.kylinhunter.nlp.dic.core.match.bean.MatchResult;
import com.kylinhunter.nlp.dic.core.match.component.WordNodeConvertor;

class DicMatchFullTest {

    static WordAnalyzer analyzer = KServices.get(WordAnalyzerType.DEFAULT);
    static DicConfig dicConfig = ConfigHelper.get().getDics().get(DicType.SENSITIVE);
    static DicMatch dicMatch;

    @BeforeAll
    static void init() {

        DictionaryGroup dictionaryGroup = new DictionaryGroup(dicConfig);

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