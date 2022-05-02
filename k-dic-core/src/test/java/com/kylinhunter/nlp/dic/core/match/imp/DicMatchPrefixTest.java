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
import com.kylinhunter.nlp.dic.core.match.TestCaseDicMatchPrefix;
import com.kylinhunter.nlp.dic.core.match.TestDicMatchHelper;
import com.kylinhunter.nlp.dic.core.match.bean.MatchResult;
import com.kylinhunter.nlp.dic.core.match.bean.WordNode;
import com.kylinhunter.nlp.dic.core.match.component.WordNodeConvertor;

class DicMatchPrefixTest {

    static WordAnalyzer analyzer = KServices.get(WordAnalyzerType.DEFAULT);
    static DicConfig dicConfig = ConfigHelper.get().getDics().get(DicType.COMPLETE);
    static DicMatch dicMatch;

    @BeforeAll
    static void init() {

        DictionaryGroup<WordNode> dictionaryGroup = new DictionaryGroup(dicConfig);

        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "北京", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "北京人", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "北京地铁", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "北京欢迎您", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "北京欢迎你", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "北京欢乐一家亲", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "北京海淀", "", "", analyzer, 10));

        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.MIDDLE, "北京欢乐谷", "", "", analyzer, 10));

        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.LOW, "北京欢乐大世界", "", "", analyzer, 10));

        dicMatch = DicMatchCreator.create(dicConfig.getDicMatchType(), dictionaryGroup);

    }

    @Test
    void processF1Char() {

        String text = "北";
        FindLevel findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));


        text = "北**";

        findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));


        text = "北**1**";
        findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));

    }

    @Test
    void processF2Char() {

        String text = "北京";


        FindLevel findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));

        text = "北**京";

        findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));


        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));

        text = "北**1**京";

        findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));


        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));


        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));

    }

    @Test
    void processBack3Char() {

        String text = "我爱北京欢";
        FindLevel findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));


        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));


        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));


        text = "我爱北京**欢";
        findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));


        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));


        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));


        text = "我爱北京**1**欢";
        findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));


        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));


        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dicMatch.match(text, findLevel)));

    }

    @Test
    void processNull() {
        String text = "我其实很爱";
        FindLevel findLevel = FindLevel.HIGH;
        List<MatchResult> matchResults = dicMatch.match(text, findLevel);
        List<String> resultString = TestDicMatchHelper.toString(text, findLevel, matchResults);
        Assertions.assertNull(resultString);

        text = "我其实很**爱";
        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dicMatch.match(text, findLevel);
        resultString = TestDicMatchHelper.toString(text, findLevel, matchResults);
        Assertions.assertNull(resultString);

        text = "我其实很**1**爱";
        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        matchResults = dicMatch.match(text, findLevel);
        resultString = TestDicMatchHelper.toString(text, findLevel, matchResults);
        Assertions.assertNull(resultString);

    }


}