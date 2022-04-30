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
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;
import com.kylinhunter.nlp.dic.core.dictionary.group.DictionaryGroup;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.HitMode;
import com.kylinhunter.nlp.dic.core.loader.constants.DicType;
import com.kylinhunter.nlp.dic.core.match.DicMatch;
import com.kylinhunter.nlp.dic.core.match.DicMatchCreator;
import com.kylinhunter.nlp.dic.core.match.DicMatchType;
import com.kylinhunter.nlp.dic.core.match.bean.MatchResult;
import com.kylinhunter.nlp.dic.core.match.bean.WordNode;
import com.kylinhunter.nlp.dic.core.match.component.WordNodeConvertor;

class DicMatchPrefixTest {

    static WordAnalyzer analyzer = KServices.get(WordAnalyzerType.DEFAULT);
    static DicConfig dicConfig = ConfigHelper.get().getDics().get(DicType.SENSITIVE);
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
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.HIGH, "河北", "廊坊,张家口", "", analyzer, 10));

        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.MIDDLE, "乌鲁木齐", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.MIDDLE, "北京欢乐谷", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.MIDDLE, "呼和浩特", "新疆", "", analyzer, 10));

        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.LOW, "上海", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.LOW, "北京欢乐大世界", "", "", analyzer, 10));
        dictionaryGroup.put(WordNodeConvertor.convert(HitMode.LOW, "山西", "大同府,阎王寨", "", analyzer, 10));

        dicMatch = DicMatchCreator.create(DicMatchType.PREFIX, dictionaryGroup);

    }

    String texts[] = new String[] {
            "北",// 1个字顶头中
            "北**",
            "北**1**",
            "北京",// 两个字顶头中
            "北**京",
            "北**1**京",
            "我爱北京欢", // 倒数3个中
            "我爱北京**欢",
            "我爱北京**1**欢"

    };

    @Test
    void processF1Char() {

        String text = "北";
        String[] expect = new String[] {
                "0:0:1:北京:北京:null",
                "0:0:1:北京人:北京人:null",
                "0:0:1:北京地铁:北京地铁:null",
                "0:0:1:北京欢乐一家亲:北京欢乐一家亲:null",
                "0:0:1:北京欢乐大世界:北京欢乐大世界:null",
                "0:0:1:北京欢乐谷:北京欢乐谷:null",
                "0:0:1:北京欢迎你:北京欢迎你:null",
                "0:0:1:北京欢迎您:北京欢迎您:null",
                "0:0:1:北京海淀:北京海淀:null"
        };

        FindLevel findLevel = FindLevel.HIGH;
        List<MatchResult> matchResults = dicMatch.match(text, findLevel);
        List<String> resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        text = "北**";

        findLevel = FindLevel.HIGH;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertEquals(null, resultString);

        expect = new String[] {
                "0:0:1:北京欢乐大世界:北京欢乐大世界:null",
                "0:0:1:北京欢乐谷:北京欢乐谷:null",
        };
        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        text = "北**1**";
        findLevel = FindLevel.HIGH;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertEquals(null, resultString);

        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertEquals(null, resultString);

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertEquals(null, resultString);
    }

    @Test
    void processF2Char() {

        String text = "北京";
        String[] expect = new String[] {
                "0:0:2:北京:北京:null",
                "0:0:2:北京人:北京人:null",
                "0:0:2:北京地铁:北京地铁:null",
                "0:0:2:北京欢乐一家亲:北京欢乐一家亲:null",
                "0:0:2:北京欢乐大世界:北京欢乐大世界:null",
                "0:0:2:北京欢乐谷:北京欢乐谷:null",
                "0:0:2:北京欢迎你:北京欢迎你:null",
                "0:0:2:北京欢迎您:北京欢迎您:null",
                "0:0:2:北京海淀:北京海淀:null"
        };

        FindLevel findLevel = FindLevel.HIGH;
        List<MatchResult> matchResults = dicMatch.match(text, findLevel);
        List<String> resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        text = "北**京";

        findLevel = FindLevel.HIGH;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertEquals(null, resultString);

        expect = new String[] {
                "0:0:4:北京欢乐大世界:北京欢乐大世界:null",
                "0:0:4:北京欢乐谷:北京欢乐谷:null",
        };
        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        text = "北**1**京";

        findLevel = FindLevel.HIGH;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertEquals(null, resultString);

        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertEquals(null, resultString);

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        expect = new String[] {
                "0:0:7:北京欢乐大世界:北京欢乐大世界:null"
        };
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());
    }

    @Test
    void processBack3Char() {

        String text = "我爱北京欢";
        String[] expect = new String[] {
                "0:2:5:我爱北京欢乐一家亲:北京欢乐一家亲:null",
                "0:2:5:我爱北京欢乐大世界:北京欢乐大世界:null",
                "0:2:5:我爱北京欢乐谷:北京欢乐谷:null",
                "0:2:5:我爱北京欢迎你:北京欢迎你:null",
                "0:2:5:我爱北京欢迎您:北京欢迎您:null"
        };

        FindLevel findLevel = FindLevel.HIGH;
        List<MatchResult> matchResults = dicMatch.match(text, findLevel);
        List<String> resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        text = "我爱北京**欢";

        findLevel = FindLevel.HIGH;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertEquals(null, resultString);

        expect = new String[] {
                "0:2:7:我爱北京欢乐大世界:北京欢乐大世界:null",
                "0:2:7:我爱北京欢乐谷:北京欢乐谷:null",
        };
        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        text = "我爱北京**1**欢";

        findLevel = FindLevel.HIGH;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertEquals(null, resultString);

        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertEquals(null, resultString);

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        expect = new String[] {
                "0:2:10:我爱北京欢乐大世界:北京欢乐大世界:null"
        };
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());
    }




    @Test
    void processNull() {
        String text = "我其实很爱";
        FindLevel findLevel = FindLevel.HIGH;
        List<MatchResult> matchResults = dicMatch.match(text, findLevel);
        List<String> resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertNull(resultString);

        text = "我其实很**爱";
        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertNull(resultString);

        text = "我其实很**1**爱";
        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        matchResults = dicMatch.match(text, findLevel);
        resultString = DicMatchFullTest.printResult(text, findLevel, matchResults);
        Assertions.assertNull(resultString);

    }

}