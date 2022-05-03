package com.kylinhunter.nlp.dic.core.match;

import java.util.Map;

import com.google.common.collect.Maps;
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;

/**
 * @author BiJi'an
 * @description
 * @create 2022-01-03 00:21
 **/
public class TestCaseDicMatchFull {

    private static Map<FindLevel, String[]> datas = Maps.newHashMap();

    public static String text = "北京和北**京和北**1**京"
            + "北京海淀和北**京海淀和北**1**京海淀"
            + "河北和河**北和河**1**北和廊坊和张家口"
            + "乌鲁木齐和乌鲁**木齐和乌鲁**1**木齐"
            + "呼和浩特和呼和**浩特和呼和**1**浩特和新疆"
            + "上海和上**海和上**1**海"
            + "山西和山**西和山**1**西大同府和阎王寨";

    static {
        datas.put(FindLevel.HIGH, new String[] {
                "1:0:2:北京:北京:null",
                "1:15:17:北京:北京:null",
                "1:15:19:北京海淀:北京海淀:null",
                "1:36:38:河北:河北:[廊坊, 张家口]",
                "1:58:62:乌鲁木齐:乌鲁木齐:null",
                "1:79:83:呼和浩特:呼和浩特:[新疆]",
                "1:103:105:上海:上海:null",
                "1:118:120:山西:山西:[大同府, 阎王寨]"
        });
        datas.put(FindLevel.HIGH_MIDDLE, new String[] {
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
        });
        datas.put(FindLevel.HIGH_MIDDLE_LOW, new String[] {
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
        });
    }

    public static String[] get(FindLevel findLevel) {
        return datas.get(findLevel);
    }

}
