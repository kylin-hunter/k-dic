package io.github.kylinhunter.tools.dic.app.imp;

import java.util.Map;

import com.google.common.collect.Maps;

import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;

/**
 * @author BiJi'an
 * @description
 * @date 2022/12/5
 **/
public class EnglishTestCaseDicMatchFull {

    private static final Map<FindLevel, String[]> datas = Maps.newHashMap();

    public static String text = "kylin and ky**lin and ky**1**lin "
            + " kylinOS and ky**linOS and ky**1**linOS"
            + " teacher and tea**cher and tea*t*cher and male and tall"
            + " fox and fo**x and fo*t*x"
            + " dog and do**g and do**t**g and cat"
            + " movie and mo**vie an mo*t*vie"
            + " star and st**ar and st*t*ar and jackie and bruce"; // text

    static {
        datas.put(FindLevel.HIGH, new String[] {
                "1:0:5:kylin:kylin:null",
                "1:34:39:kylin:kylin:null",
                "1:34:41:kylinOS:kylinOS:null",
                "1:73:80:teacher:teacher:[male, tall]",
                "1:128:131:fox:fox:null",
                "1:153:156:dog:dog:[cat]",
                "1:188:193:movie:movie:null",
                "1:218:222:star:star:[jackie, bruce]"
        });

        datas.put(FindLevel.HIGH_MIDDLE, new String[] {
                "1:0:5:kylin:kylin:null",
                "1:34:39:kylin:kylin:null",
                "1:34:41:kylinOS:kylinOS:null",
                "1:73:80:teacher:teacher:[male, tall]",
                "1:128:131:fox:fox:null",
                "2:136:141:fo**x:fox:null",
                "1:153:156:dog:dog:[cat]",
                "2:161:166:do**g:dog:[cat]",
                "1:188:193:movie:movie:null",
                "2:198:205:mo**vie:movie:null",
                "1:218:222:star:star:[jackie, bruce]",
                "2:227:233:st**ar:star:[jackie, bruce]"
        });

        datas.put(FindLevel.HIGH_MIDDLE_LOW, new String[] {
                "1:0:5:kylin:kylin:null",
                "1:34:39:kylin:kylin:null",
                "1:34:41:kylinOS:kylinOS:null",
                "1:73:80:teacher:teacher:[male, tall]",
                "1:128:131:fox:fox:null",
                "2:136:141:fo**x:fox:null",
                "1:153:156:dog:dog:[cat]",
                "2:161:166:do**g:dog:[cat]",
                "1:188:193:movie:movie:null",
                "2:198:205:mo**vie:movie:null",
                "3:209:217:mo*t*vie:movie:null",
                "1:218:222:star:star:[jackie, bruce]",
                "2:227:233:st**ar:star:[jackie, bruce]",
                "3:238:245:st*t*ar:star:[jackie, bruce]"
        });
    }

    public static String[] get(FindLevel findLevel) {
        return datas.get(findLevel);
    }

}
