package com.kylinhunter.nlp.dic.core.dic;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.kylinhunter.nlp.dic.core.dic.constants.DicType;
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;
import com.kylinhunter.nlp.dic.core.match.TestDicMatchHelper;
import com.kylinhunter.nlp.dic.core.match.bean.MatchResult;

class DicTypeCompleteTest {

    static Dic dic;

    @BeforeAll
    static void init() {
        dic = DicManager.get(DicType.COMPLETE);
    }


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
        List<MatchResult> matchResults = dic.match(text, findLevel);
        List<String> resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        text = "北**";

        findLevel = FindLevel.HIGH;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertEquals(null, resultString);

        expect = new String[] {
                "0:0:1:北京欢乐大世界:北京欢乐大世界:null",
                "0:0:1:北京欢乐谷:北京欢乐谷:null",
        };
        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        text = "北**1**";
        findLevel = FindLevel.HIGH;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertEquals(null, resultString);

        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertEquals(null, resultString);

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
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
        List<MatchResult> matchResults = dic.match(text, findLevel);
        List<String> resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        text = "北**京";

        findLevel = FindLevel.HIGH;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertEquals(null, resultString);

        expect = new String[] {
                "0:0:4:北京欢乐大世界:北京欢乐大世界:null",
                "0:0:4:北京欢乐谷:北京欢乐谷:null",
        };
        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        text = "北**1**京";

        findLevel = FindLevel.HIGH;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertEquals(null, resultString);

        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertEquals(null, resultString);

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        expect = new String[] {
                "0:0:7:北京欢乐大世界:北京欢乐大世界:null"
        };
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
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
        List<MatchResult> matchResults = dic.match(text, findLevel);
        List<String> resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        text = "我爱北京**欢";

        findLevel = FindLevel.HIGH;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertEquals(null, resultString);

        expect = new String[] {
                "0:2:7:我爱北京欢乐大世界:北京欢乐大世界:null",
                "0:2:7:我爱北京欢乐谷:北京欢乐谷:null",
        };
        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());

        text = "我爱北京**1**欢";

        findLevel = FindLevel.HIGH;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertEquals(null, resultString);

        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertEquals(null, resultString);

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        expect = new String[] {
                "0:2:10:我爱北京欢乐大世界:北京欢乐大世界:null"
        };
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(expect, resultString.toArray());
    }

    @Test
    void processNull() {
        String text = "我其实很爱";
        FindLevel findLevel = FindLevel.HIGH;
        List<MatchResult> matchResults = dic.match(text, findLevel);
        List<String> resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertNull(resultString);

        text = "我其实很**爱";
        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertNull(resultString);

        text = "我其实很**1**爱";
        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        matchResults = dic.match(text, findLevel);
        resultString = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertNull(resultString);

    }
}