package io.github.kylinhunter.tools.dic.core.match;

import java.util.Map;

import com.google.common.collect.Maps;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;

/**
 * @author BiJi'an
 * @description
 * @create 2022-01-03 00:21
 **/
public class TestCaseDicMatchPrefix {

    private static Map<String, String[]> datas = Maps.newHashMap();

    static {
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
        put(text, FindLevel.HIGH, expect);
        put(text, FindLevel.HIGH_MIDDLE, expect);
        put(text, FindLevel.HIGH_MIDDLE_LOW, expect);

        text = "北**";
        expect = new String[] {
                "0:0:1:北京欢乐大世界:北京欢乐大世界:null",
                "0:0:1:北京欢乐谷:北京欢乐谷:null",
        };
        put(text, FindLevel.HIGH, null);
        put(text, FindLevel.HIGH_MIDDLE, expect);
        put(text, FindLevel.HIGH_MIDDLE_LOW, expect);

        text = "北**1**";
        put(text, FindLevel.HIGH, null);
        put(text, FindLevel.HIGH_MIDDLE, null);
        put(text, FindLevel.HIGH_MIDDLE_LOW, null);

        text = "北京";
        expect = new String[] {
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
        put(text, FindLevel.HIGH, expect);
        put(text, FindLevel.HIGH_MIDDLE, expect);
        put(text, FindLevel.HIGH_MIDDLE_LOW, expect);

        text = "北**京";
        expect = new String[] {
                "0:0:4:北京欢乐大世界:北京欢乐大世界:null",
                "0:0:4:北京欢乐谷:北京欢乐谷:null",
        };
        put(text, FindLevel.HIGH, null);
        put(text, FindLevel.HIGH_MIDDLE, expect);
        put(text, FindLevel.HIGH_MIDDLE_LOW, expect);

        text = "北**1**京";
        expect = new String[] {
                "0:0:7:北京欢乐大世界:北京欢乐大世界:null"
        };
        put(text, FindLevel.HIGH, null);
        put(text, FindLevel.HIGH_MIDDLE, null);
        put(text, FindLevel.HIGH_MIDDLE_LOW, expect);





        text = "我爱北京欢";
        expect = new String[] {
                "0:2:5:我爱北京欢乐一家亲:北京欢乐一家亲:null",
                "0:2:5:我爱北京欢乐大世界:北京欢乐大世界:null",
                "0:2:5:我爱北京欢乐谷:北京欢乐谷:null",
                "0:2:5:我爱北京欢迎你:北京欢迎你:null",
                "0:2:5:我爱北京欢迎您:北京欢迎您:null"
        };
        put(text, FindLevel.HIGH, expect);
        put(text, FindLevel.HIGH_MIDDLE, expect);
        put(text, FindLevel.HIGH_MIDDLE_LOW, expect);

        text = "我爱北京**欢";
        expect = new String[] {
                "0:2:7:我爱北京欢乐大世界:北京欢乐大世界:null",
                "0:2:7:我爱北京欢乐谷:北京欢乐谷:null",
        };
        put(text, FindLevel.HIGH, null);
        put(text, FindLevel.HIGH_MIDDLE, expect);
        put(text, FindLevel.HIGH_MIDDLE_LOW, expect);

        text = "我爱北京**1**欢";
        expect = new String[] {
                "0:2:10:我爱北京欢乐大世界:北京欢乐大世界:null"
        };
        put(text, FindLevel.HIGH, null);
        put(text, FindLevel.HIGH_MIDDLE, null);
        put(text, FindLevel.HIGH_MIDDLE_LOW, expect);



    }

    public static void put(String text, FindLevel findLevel, String[] expect) {
        datas.put(text + "_" + findLevel, expect);
    }

    public static String[] get(String text, FindLevel findLevel) {
        return datas.get(text + "_" + findLevel);
    }

}
