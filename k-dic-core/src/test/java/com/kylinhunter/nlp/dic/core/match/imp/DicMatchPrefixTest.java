package com.kylinhunter.nlp.dic.core.match.imp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.kylinhunter.nlp.dic.commons.service.KServices;
import com.kylinhunter.nlp.dic.core.analyzer.WordAnalyzer;
import com.kylinhunter.nlp.dic.core.analyzer.WordAnalyzerType;
import com.kylinhunter.nlp.dic.core.config.ConfigHelper;
import com.kylinhunter.nlp.dic.core.config.DicConfig;
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;
import com.kylinhunter.nlp.dic.core.dictionary.group.DictionaryGroup;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.HitMode;
import com.kylinhunter.nlp.dic.core.loader.constants.DicType;
import com.kylinhunter.nlp.dic.core.match.DicMatch;
import com.kylinhunter.nlp.dic.core.match.DicMatchCreator;
import com.kylinhunter.nlp.dic.core.match.DicMatchType;
import com.kylinhunter.nlp.dic.core.match.bean.MatchResult;
import com.kylinhunter.nlp.dic.core.match.component.WordNodeConvertor;

class DicMatchPrefixTest {

    static WordAnalyzer analyzer = KServices.get(WordAnalyzerType.DEFAULT);
    static DicConfig dicConfig = ConfigHelper.get().getDics().get(DicType.SENSITIVE);
    static DicMatch dicMatch;

    @BeforeAll
    static void init() {

        DictionaryGroup dictionaryGroup = new DictionaryGroup(dicConfig);

        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "北京", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "北京人", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "北京地铁", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "北京欢迎您", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "北京欢迎你", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "北京欢乐一家亲", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "北京海淀", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "河北", "廊坊,张家口", "", analyzer, 10));

        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.MIDDLE, "乌鲁木齐", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.MIDDLE, "呼和浩特", "新疆", "", analyzer, 10));

        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.LOW, "上海", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.LOW, "山西", "大同府,阎王寨", "", analyzer, 10));

        dicMatch = DicMatchCreator.create(DicMatchType.PREFIX, dictionaryGroup);

    }

    List<String> toStringList(List<MatchResult> matchResults) {
        return matchResults.stream()
                .map(e -> e.getMatchLevel() + ":" + e.getStart() + ":" + e.getEnd() + ":" + e.getHitWord() + ":" + e
                        .getMatchWord() + ":" + Arrays.toString(e.getAssistWords())).collect(Collectors.toList());
    }

    @Test
    void processHigh() {

        String text = "北京";

        List<MatchResult> matchResults = dicMatch.match(text, FindLevel.HIGH);
        List<String> resultString = DicMatchFullTest.toResultString(matchResults);
        Assertions.assertArrayEquals(new String[] {
                        "0:0:2:北京:北京:null",
                        "0:0:2:北京人:北京人:null",
                        "0:0:2:北京地铁:北京地铁:null",
                        "0:0:2:北京欢乐一家亲:北京欢乐一家亲:null",
                        "0:0:2:北京欢迎你:北京欢迎你:null",
                        "0:0:2:北京欢迎您:北京欢迎您:null",
                        "0:0:2:北京海淀:北京海淀:null"
                },
                resultString.toArray());

        text = "我爱北京欢";

        matchResults = dicMatch.match(text, FindLevel.HIGH);
        resultString = DicMatchFullTest.toResultString(matchResults);
        Assertions.assertArrayEquals(new String[] {
                        "0:2:5:我爱北京欢乐一家亲:北京欢乐一家亲:null",
                        "0:2:5:我爱北京欢迎你:北京欢迎你:null",
                        "0:2:5:我爱北京欢迎您:北京欢迎您:null"
                },
                resultString.toArray());

    }

}