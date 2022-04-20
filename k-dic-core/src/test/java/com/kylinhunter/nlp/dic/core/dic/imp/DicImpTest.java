package com.kylinhunter.nlp.dic.core.dic.imp;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.kylinhunter.nlp.dic.core.dic.Dic;
import com.kylinhunter.nlp.dic.core.loader.LocalDicLoader;
import com.kylinhunter.nlp.dic.core.loader.constants.DicType;
import com.kylinhunter.nlp.dic.core.dic.bean.MatchResult;
import com.kylinhunter.nlp.dic.core.dic.option.MatchOptions;

class DicImpTest {

    @BeforeAll
    static void init() {
    }

    @Test
    void process1() {

        Dic dic = LocalDicLoader.getInstance().load(DicType.SENSITIVE);
        String text = "我猜测"
                + "北京和北*京和北*1京人民"
                + "上海和上*海和上*1海黄浦区大钟寺人民"
                + "和石家庄和石家*庄和石家*1庄人民"
                + "保定和保*定和保*1定冉庄人民"
                + "和乌鲁木齐和乌鲁木*齐和乌鲁木*1齐人民"
                + "大同和大*同和大*1同阎王寨人民";

        List<MatchResult> matchResults = dic.match(text, MatchOptions.FULL_HIGH);
        System.out.println("=====high");
        matchResults.forEach(System.out::println);
        Assertions.assertTrue(matchResults != null && matchResults.size() == 6);

        matchResults = dic.match(text, MatchOptions.FULL_MIDDLE);
        System.out.println("===== middle");
        matchResults.forEach(System.out::println);
        Assertions.assertTrue(matchResults != null && matchResults.size() == 10);

        matchResults = dic.match(text,  MatchOptions.FULL_LOW);
        System.out.println("===== low");
        matchResults.forEach(System.out::println);
        Assertions.assertTrue(matchResults != null && matchResults.size() == 12);

    }

}