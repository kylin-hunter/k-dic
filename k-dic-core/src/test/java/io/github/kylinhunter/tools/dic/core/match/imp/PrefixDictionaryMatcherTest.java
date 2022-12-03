package io.github.kylinhunter.tools.dic.core.match.imp;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.tools.dic.core.dictionary.DictionaryGroup;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.HitMode;
import io.github.kylinhunter.tools.dic.core.dictionary.helper.WordNodeConvertor;
import io.github.kylinhunter.tools.dic.core.match.DictionaryMatcher;
import io.github.kylinhunter.tools.dic.core.match.DictionaryMatcherFactory;
import io.github.kylinhunter.tools.dic.core.match.DictionaryMatcherType;
import io.github.kylinhunter.tools.dic.core.match.TestCaseDicMatchPrefix;
import io.github.kylinhunter.tools.dic.core.match.TestDicMatchHelper;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;
import io.github.kylinhunter.tools.dic.words.analyzer.WordAnalyzer;
import io.github.kylinhunter.tools.dic.words.analyzer.WordAnalyzerType;

class PrefixDictionaryMatcherTest {

    static WordAnalyzer analyzer = CF.get(WordAnalyzerType.DEFAULT.clazz);
    static DictionaryMatcher dictionaryMatcher;

    @BeforeAll
    static void init() {

        DictionaryGroup dictionaryGroup = new DictionaryGroup();

        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "北京", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "北京人", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "北京地铁", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "北京欢迎您", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "北京欢迎你", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "北京欢乐一家亲", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "北京海淀", "", "", analyzer, 10));

        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.MIDDLE, "北京欢乐谷", "", "", analyzer, 10));

        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.LOW, "北京欢乐大世界", "", "", analyzer, 10));

        dictionaryMatcher = DictionaryMatcherFactory.create(DictionaryMatcherType.PREFIX, dictionaryGroup);

    }

    @Test
    void processF1Char() {

        String text = "北";
        FindLevel findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        text = "北**";

        findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        text = "北**1**";
        findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

    }

    @Test
    void processF2Char() {

        String text = "北京";

        FindLevel findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        text = "北**京";

        findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        text = "北**1**京";

        findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

    }

    @Test
    void processBack3Char() {

        String text = "我爱北京欢";
        FindLevel findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        text = "我爱北京**欢";
        findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        text = "我爱北京**1**欢";
        findLevel = FindLevel.HIGH;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        Assertions.assertArrayEquals(TestCaseDicMatchPrefix.get(text, findLevel),
                TestDicMatchHelper.toStringArr(text, findLevel, dictionaryMatcher.match(text, findLevel)));

    }

    @Test
    void processNull() {
        String text = "我其实很爱";
        FindLevel findLevel = FindLevel.HIGH;
        List<MatchResult> matchResults = dictionaryMatcher.match(text, findLevel);
        List<String> resultString = TestDicMatchHelper.toString(text, findLevel, matchResults);
        Assertions.assertTrue(resultString.size() == 0);

        text = "我其实很**爱";
        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dictionaryMatcher.match(text, findLevel);
        resultString = TestDicMatchHelper.toString(text, findLevel, matchResults);
        Assertions.assertTrue(resultString.size() == 0);

        text = "我其实很**1**爱";
        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        matchResults = dictionaryMatcher.match(text, findLevel);
        resultString = TestDicMatchHelper.toString(text, findLevel, matchResults);
        Assertions.assertTrue(resultString.size() == 0);

    }

}