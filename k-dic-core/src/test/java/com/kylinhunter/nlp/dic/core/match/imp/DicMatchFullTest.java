package com.kylinhunter.nlp.dic.core.match.imp;

import com.kylinhunter.nlp.dic.commons.service.KServices;
import com.kylinhunter.nlp.dic.core.analyzer.WordAnalyzer;
import com.kylinhunter.nlp.dic.core.analyzer.WordAnalyzerType;
import com.kylinhunter.nlp.dic.core.config.ConfigHelper;
import com.kylinhunter.nlp.dic.core.config.DicConfig;
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;
import com.kylinhunter.nlp.dic.core.match.DicMatch;
import com.kylinhunter.nlp.dic.core.match.DicMatchCreator;
import com.kylinhunter.nlp.dic.core.match.DicMatchType;
import com.kylinhunter.nlp.dic.core.match.bean.MatchResult;
import com.kylinhunter.nlp.dic.core.match.component.MatchWordNodeConvertor;
import com.kylinhunter.nlp.dic.core.dictionary.group.DictionaryGroup;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.HitMode;
import com.kylinhunter.nlp.dic.core.loader.constants.DicType;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class DicMatchFullTest {

    static WordAnalyzer analyzer = KServices.get(WordAnalyzerType.DEFAULT);
    static DicConfig dicConfig = ConfigHelper.get().getDics().get(DicType.SENSITIVE);
    static DicMatch dicMatch;

    @BeforeAll
    static void init() {

        DictionaryGroup dictionaryGroup = new DictionaryGroup(dicConfig);

        dictionaryGroup.put(MatchWordNodeConvertor.convert(HitMode.HIGH, "北京", "", "", analyzer, 10));
        dictionaryGroup.put(MatchWordNodeConvertor.convert(HitMode.HIGH, "北京海淀", "", "", analyzer, 10));
        dictionaryGroup.put(MatchWordNodeConvertor.convert(HitMode.HIGH, "河北", "廊坊,张家口", "", analyzer, 10));

        dictionaryGroup.put(MatchWordNodeConvertor.convert(HitMode.MIDDLE, "乌鲁木齐", "", "", analyzer, 10));
        dictionaryGroup.put(MatchWordNodeConvertor.convert(HitMode.MIDDLE, "呼和浩特", "新疆", "", analyzer, 10));

        dictionaryGroup.put(MatchWordNodeConvertor.convert(HitMode.LOW, "上海", "", "", analyzer, 10));
        dictionaryGroup.put(MatchWordNodeConvertor.convert(HitMode.LOW, "山西", "大同府,阎王寨", "", analyzer, 10));

        dicMatch = DicMatchCreator.create(DicMatchType.FULL, dictionaryGroup);

    }

    List<String> toStringList(List<MatchResult> matchResults) {
        return matchResults.stream()
                .map(e -> e.getMatchLevel() + ":" + e.getStart() + ":" + e.getEnd() + ":" + e.getWord() + ":" + e
                        .getMatchWord() + ":" + Arrays.toString(e.getAssistWords())).collect(Collectors.toList());
    }

    @Test
    void processFull() {

        String text = "北京和北**京和北**1**京"
                + "北京海淀和北**京海淀和北**1**京海淀"
                + "河北和河**北和河**1**北和廊坊和张家口"
                + "乌鲁木齐和乌鲁**木齐和乌鲁**1**木齐"
                + "呼和浩特和呼和**浩特和呼和**1**浩特和新疆"
                + "上海和上**海和上**1**海"
                + "山西和山**西和山**1**西大同府和阎王寨";

        List<MatchResult> matchResults = dicMatch.match(text, FindLevel.HIGH);
        List<String> matchResultsArr = toStringList(matchResults);
        System.out.println("=====high\n");
        matchResultsArr.forEach(System.out::println);

        Assertions.assertArrayEquals(new String[] {
                        "1:0:2:北京:北京:null",
                        "1:15:17:北京:北京:null",
                        "1:15:19:北京海淀:北京海淀:null",
                        "1:36:38:河北:河北:[廊坊, 张家口]",
                        "1:58:62:乌鲁木齐:乌鲁木齐:null",
                        "1:79:83:呼和浩特:呼和浩特:[新疆]",
                        "1:103:105:上海:上海:null",
                        "1:118:120:山西:山西:[大同府, 阎王寨]"
                },
                matchResultsArr.toArray());

        matchResults = dicMatch.match(text, FindLevel.HIGH_MIDDLE);
        matchResultsArr = toStringList(matchResults);
        System.out.println("=====middle\n");
        matchResultsArr.forEach(System.out::println);

        Assertions.assertArrayEquals(new String[] {
                        "1:0:2:北京:北京:null",
                        "1:15:17:北京:北京:null",
                        "1:15:19:北京海淀:北京海淀:null",
                        "1:36:38:河北:河北:[廊坊, 张家口]",
                        "1:58:62:乌鲁木齐:乌鲁木齐:null",
                        "2:63:69:乌鲁**木齐:乌鲁木齐:null",
                        "1:79:83:呼和浩特:呼和浩特:[新疆]",
                        "2:84:90:呼和**浩特:呼和浩特:[新疆]",
                        "1:103:105:上海:上海:null",
                        "2:106:110:上**海:上海:null",
                        "1:118:120:山西:山西:[大同府, 阎王寨]",
                        "2:121:125:山**西:山西:[大同府, 阎王寨]"
                },
                matchResultsArr.toArray());

        matchResults = dicMatch.match(text, FindLevel.HIGH_MIDDLE_LOW);
        matchResultsArr = toStringList(matchResults);
        System.out.println("=====middle\n");
        matchResultsArr.forEach(System.out::println);

        Assertions.assertArrayEquals(new String[] {
                        "1:0:2:北京:北京:null",
                        "1:15:17:北京:北京:null",
                        "1:15:19:北京海淀:北京海淀:null",
                        "1:36:38:河北:河北:[廊坊, 张家口]",
                        "1:58:62:乌鲁木齐:乌鲁木齐:null",
                        "2:63:69:乌鲁**木齐:乌鲁木齐:null",
                        "1:79:83:呼和浩特:呼和浩特:[新疆]",
                        "2:84:90:呼和**浩特:呼和浩特:[新疆]",
                        "1:103:105:上海:上海:null",
                        "2:106:110:上**海:上海:null",
                        "3:111:118:上**1**海:上海:null",
                        "1:118:120:山西:山西:[大同府, 阎王寨]",
                        "2:121:125:山**西:山西:[大同府, 阎王寨]",
                        "3:126:133:山**1**西:山西:[大同府, 阎王寨]"
                },
                matchResultsArr.toArray());

    }

}