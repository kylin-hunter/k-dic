package io.github.kylinhunter.tools.dic.core.demo;

import java.util.List;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.tools.dic.core.dictionary.DictionaryGroup;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.HitMode;
import io.github.kylinhunter.tools.dic.core.dictionary.helper.WordNodeConvertor;
import io.github.kylinhunter.tools.dic.core.match.DictionaryMatcher;
import io.github.kylinhunter.tools.dic.core.match.MatcherType;
import io.github.kylinhunter.tools.dic.core.match.TestCaseDicMatchFull;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-04 19:34
 **/
public class FullMatchDemo {
    public static void main(String[] args) {
        DictionaryMatcher dictionaryMatcher = CF.get(MatcherType.FULL);

        dictionaryMatcher.addWord(WordNodeConvertor.convert(HitMode.HIGH, "北京", "", ""));
        dictionaryMatcher.addWord(WordNodeConvertor.convert(HitMode.HIGH, "北京海淀", "", ""));
        dictionaryMatcher.addWord(WordNodeConvertor.convert(HitMode.HIGH, "河北", "廊坊,张家口", ""));

        dictionaryMatcher.addWord(WordNodeConvertor.convert(HitMode.MIDDLE, "乌鲁木齐", "", ""));
        dictionaryMatcher.addWord(WordNodeConvertor.convert(HitMode.MIDDLE, "呼和浩特", "新疆", ""));

        dictionaryMatcher.addWord(WordNodeConvertor.convert(HitMode.LOW, "上海", "", ""));
        dictionaryMatcher.addWord(WordNodeConvertor.convert(HitMode.LOW, "山西", "大同府,阎王寨", ""));


        FindLevel findLevel = FindLevel.HIGH;
        List<MatchResult> matchResults = dictionaryMatcher.match(TestCaseDicMatchFull.text, findLevel);
        matchResults.forEach(System.out::println);

        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dictionaryMatcher.match(TestCaseDicMatchFull.text, findLevel);
        matchResults.forEach(System.out::println);

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        matchResults = dictionaryMatcher.match(TestCaseDicMatchFull.text, findLevel);
        matchResults.forEach(System.out::println);

    }
}
