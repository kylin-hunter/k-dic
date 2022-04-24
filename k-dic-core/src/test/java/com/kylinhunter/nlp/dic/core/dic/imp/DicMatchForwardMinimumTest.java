package com.kylinhunter.nlp.dic.core.dic.imp;

import com.kylinhunter.nlp.dic.commons.service.KServices;
import com.kylinhunter.nlp.dic.core.analyzer.WordAnalyzer;
import com.kylinhunter.nlp.dic.core.analyzer.WordAnalyzerType;
import com.kylinhunter.nlp.dic.core.config.ConfigHelper;
import com.kylinhunter.nlp.dic.core.config.DicConfig;
import com.kylinhunter.nlp.dic.core.dic.DicMatch;
import com.kylinhunter.nlp.dic.core.dic.bean.MatchResult;
import com.kylinhunter.nlp.dic.core.dic.component.MatchWordNodeConvertor;
import com.kylinhunter.nlp.dic.core.dic.option.MatchOptions;
import com.kylinhunter.nlp.dic.core.dictionary.group.DictionaryGroup;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.HitMode;
import com.kylinhunter.nlp.dic.core.loader.Dic;
import com.kylinhunter.nlp.dic.core.loader.DicManager;
import com.kylinhunter.nlp.dic.core.loader.common.DicDataHelper;
import com.kylinhunter.nlp.dic.core.loader.constants.DicType;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class DicMatchForwardMinimumTest {

    static DicMatch dicMatch = new DicMatchForwardMinimum();
    static WordAnalyzer analyzer = KServices.get(WordAnalyzerType.DEFAULT);
    static DicConfig dicConfig = ConfigHelper.get().getDics().get(DicType.SENSITIVE);

    @BeforeAll
    static void init() {

        DictionaryGroup dictionaryGroup = new DictionaryGroup(dicConfig);

        dictionaryGroup.put(MatchWordNodeConvertor.convert(HitMode.HIGH, "北京", "", "", analyzer, 10));
        dictionaryGroup.put(MatchWordNodeConvertor.convert(HitMode.HIGH, "河北", "廊坊,张家口", "", analyzer, 10));

        dictionaryGroup.put(MatchWordNodeConvertor.convert(HitMode.MIDDLE, "乌鲁木齐", "", "", analyzer, 10));
        dictionaryGroup.put(MatchWordNodeConvertor.convert(HitMode.MIDDLE, "呼和浩特", "新疆", "", analyzer, 10));

        dictionaryGroup.put(MatchWordNodeConvertor.convert(HitMode.LOW, "上海", "", "", analyzer, 10));
        dictionaryGroup.put(MatchWordNodeConvertor.convert(HitMode.LOW, "山西", "大同府,阎王寨", "", analyzer, 10));

        dicMatch.init(dictionaryGroup, analyzer);

    }

    @Test
    void processFull() {

        String text = "北京和北**京和北**1**京"
                + "河北和河**北和河**1**北和廊坊和张家口"
                + "乌鲁木齐和乌鲁**木齐和乌鲁**1**木齐"
                + "呼和浩特和呼和**浩特和呼和**1**浩特和新疆"
                + "上海和上**海和上**1**海"
                + "山西和山**西和山**1**西大同府和阎王寨";

        List<MatchResult> matchResults = dicMatch.match(text, MatchOptions.FULL_HIGH);
        System.out.println("=====high");
        matchResults.forEach(e -> System.out.println(e.getWord() + ":" + Arrays.toString(e.getSencondaryWords())));
        Assertions.assertArrayEquals(new String[] {
                        "1:北京:null",
                        "1:河北:[廊坊, 张家口]",
                        "1:乌鲁木齐:null",
                        "1:呼和浩特:[新疆]",
                        "1:上海:null",
                        "1:山西:[大同府, 阎王寨]"
                },
                matchResults.stream()
                        .map(e -> e.getMatchLevel() + ":" + e.getWord() + ":" + Arrays.toString(e.getSencondaryWords()))
                        .collect(Collectors.toList()).toArray(new String[0]));

        matchResults = dicMatch.match(text, MatchOptions.FULL_MIDDLE);
        System.out.println("===== middle");
        matchResults.forEach(e -> System.out.println(e.getWord() + ":" + Arrays.toString(e.getSencondaryWords())));
        Assertions.assertArrayEquals(new String[] {
                        "1:北京:null",
                        "1:河北:[廊坊, 张家口]",
                        "1:乌鲁木齐:null",
                        "2:乌鲁**木齐:null",
                        "1:呼和浩特:[新疆]",
                        "2:呼和**浩特:[新疆]",
                        "1:上海:null",
                        "2:上**海:null",
                        "1:山西:[大同府, 阎王寨]",
                        "2:山**西:[大同府, 阎王寨]"
                },
                matchResults.stream()
                        .map(e -> e.getMatchLevel() + ":" + e.getWord() + ":" + Arrays.toString(e.getSencondaryWords()))
                        .collect(Collectors.toList()).toArray(new String[0]));

        matchResults = dicMatch.match(text, MatchOptions.FULL_LOW);
        System.out.println("===== low");
        matchResults.forEach(e -> System.out.println(e.getWord() + ":" + Arrays.toString(e.getSencondaryWords())));
        Assertions.assertArrayEquals(new String[] {
                        "1:北京:null",
                        "1:河北:[廊坊, 张家口]",
                        "1:乌鲁木齐:null",
                        "2:乌鲁**木齐:null",
                        "1:呼和浩特:[新疆]",
                        "2:呼和**浩特:[新疆]",
                        "1:上海:null",
                        "2:上**海:null",
                        "3:上**1**海:null",
                        "1:山西:[大同府, 阎王寨]",
                        "2:山**西:[大同府, 阎王寨]",
                        "3:山**1**西:[大同府, 阎王寨]"
                },
                matchResults.stream()
                        .map(e -> e.getMatchLevel() + ":" + e.getWord() + ":" + Arrays.toString(e.getSencondaryWords()))
                        .collect(Collectors.toList()).toArray(new String[0]));

    }

}