package com.kylinhunter.nlp.dic.core.dic;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.kylinhunter.nlp.dic.core.dic.constants.DicType;
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;
import com.kylinhunter.nlp.dic.core.match.TestDicMatchHelper;
import com.kylinhunter.nlp.dic.core.match.bean.MatchResult;

class DicTypSensitiveTest {

    static Dic dic;

    @BeforeAll
    static void init() {
        dic = DicManager.get(DicType.SENSITIVE);
    }

    String text = "北京和北**京和北**1**京"
            + "北京海淀和北**京海淀和北**1**京海淀"
            + "河北和河**北和河**1**北和廊坊和张家口"
            + "乌鲁木齐和乌鲁**木齐和乌鲁**1**木齐"
            + "呼和浩特和呼和**浩特和呼和**1**浩特和新疆"
            + "上海和上**海和上**1**海"
            + "山西和山**西和山**1**西大同府和阎王寨";

    @Test
    void processHigh() {

        List<MatchResult> matchResults = dic.match(text, FindLevel.HIGH);
        List<String> resultString = TestDicMatchHelper.printResult(text, FindLevel.HIGH, matchResults);
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
                resultString.toArray());

    }

    @Test
    void processHighMiddle() {

        List<MatchResult> matchResults = dic.match(text, FindLevel.HIGH_MIDDLE);
        List<String> resultString = TestDicMatchHelper.printResult(text, FindLevel.HIGH_MIDDLE, matchResults);

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
                resultString.toArray());

    }

    @Test
    void processHighMiddleLow() {

        List<MatchResult> matchResults = dic.match(text, FindLevel.HIGH_MIDDLE_LOW);
        List<String> resultString = TestDicMatchHelper.printResult(text, FindLevel.HIGH_MIDDLE_LOW, matchResults);
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
                resultString.toArray());

    }

    @Test
    void processNull() {

        List<MatchResult> matchResults = dic.match("hello", FindLevel.HIGH_MIDDLE_LOW);
        TestDicMatchHelper.printResult("hello", FindLevel.HIGH_MIDDLE_LOW, matchResults);
        Assertions.assertNull(matchResults);

    }
}