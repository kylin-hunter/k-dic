package io.github.kylinhunter.tools.dic.core.match.imp;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.tools.dic.core.dictionary.bean.WordNode;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.HitMode;
import io.github.kylinhunter.tools.dic.core.dictionary.helper.WordNodeConvertor;
import io.github.kylinhunter.tools.dic.core.match.DictionaryMatcher;
import io.github.kylinhunter.tools.dic.core.match.TestCaseDicMatchFull;
import io.github.kylinhunter.tools.dic.core.match.TestDicMatchHelper;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;

class FullDictionaryMatcherTest {

    static DictionaryMatcher<WordNode, WordNode> dictionaryMatcher;

    @BeforeAll
    static void init() {

        dictionaryMatcher = new FullDictionaryMatcher<>();

        dictionaryMatcher.addWord(WordNodeConvertor.convert(HitMode.HIGH, "北京", "", ""));
        dictionaryMatcher.addWord(WordNodeConvertor.convert(HitMode.HIGH, "北京海淀", "", ""));
        dictionaryMatcher.addWord(WordNodeConvertor.convert(HitMode.HIGH, "河北", "廊坊,张家口", ""));

        dictionaryMatcher.addWord(WordNodeConvertor.convert(HitMode.MIDDLE, "乌鲁木齐", "", ""));
        dictionaryMatcher.addWord(WordNodeConvertor.convert(HitMode.MIDDLE, "呼和浩特", "新疆", ""));

        dictionaryMatcher.addWord(WordNodeConvertor.convert(HitMode.LOW, "上海", "", ""));
        dictionaryMatcher.addWord(WordNodeConvertor.convert(HitMode.LOW, "山西", "大同府,阎王寨", ""));

    }

    @Test
    void processHigh() {
        FindLevel findLevel = FindLevel.HIGH;
        List<MatchResult<WordNode>> matchResults = dictionaryMatcher.match(TestCaseDicMatchFull.text, findLevel);
        Assertions.assertArrayEquals(TestCaseDicMatchFull.get(findLevel),
                TestDicMatchHelper.toStringArr(TestCaseDicMatchFull.text, findLevel, matchResults));

    }

    @Test
    void processHighMiddle() {

        FindLevel findLevel = FindLevel.HIGH_MIDDLE;
        List<MatchResult<WordNode>> matchResults = dictionaryMatcher.match(TestCaseDicMatchFull.text, findLevel);
        Assertions.assertArrayEquals(TestCaseDicMatchFull.get(findLevel),
                TestDicMatchHelper.toStringArr(TestCaseDicMatchFull.text, findLevel, matchResults));
    }

    @Test
    void processHighMiddleLow() {

        FindLevel findLevel = FindLevel.HIGH_MIDDLE_LOW;
        List<MatchResult<WordNode>> matchResults = dictionaryMatcher.match(TestCaseDicMatchFull.text, findLevel);
        Assertions.assertArrayEquals(TestCaseDicMatchFull.get(findLevel),
                TestDicMatchHelper.toStringArr(TestCaseDicMatchFull.text, findLevel, matchResults));

    }

    @Test
    void processNull() {
        String text = "hello";
        List<MatchResult<WordNode>> matchResults = dictionaryMatcher.match(text, FindLevel.HIGH_MIDDLE_LOW);
        Assertions
                .assertEquals(0, TestDicMatchHelper.toStringArr(text, FindLevel.HIGH_MIDDLE_LOW, matchResults).length);

    }

}