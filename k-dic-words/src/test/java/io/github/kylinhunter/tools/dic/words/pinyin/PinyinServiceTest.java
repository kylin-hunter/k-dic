package io.github.kylinhunter.tools.dic.words.pinyin;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.component.CF;

class PinyinServiceTest {
    @Test
    public void testBasic() {
        PinyinService service = CF.get(PinyinServiceBasic.class);

        String str = "毕继安";
        String pinyin = service.toPinyin(str);
        System.out.println(str + ":" + pinyin);
        assertEquals("bijian", pinyin);

        str = "女";
        pinyin = service.toPinyin(str.charAt(0));
        System.out.println(str.charAt(0) + ":" + pinyin);
        assertEquals("nv", pinyin);

        str = "和";
        String[] pinyins = service.toPinyins(str.charAt(0));
        System.out.println(str.charAt(0) + ":" + StringUtils.join(pinyins, ","));
        assertArrayEquals("he,he,huo,huo,huo,hai,he,hu".split(","), pinyins);

        str = "和";
        pinyin = service.toPinyin(str.charAt(0));
        System.out.println(str.charAt(0) + ":" + pinyin);
        assertEquals("he", pinyin);

        str = "毕继安123和456";
        pinyin = service.toPinyin(str);
        System.out.println(str + ":" + pinyin);
        assertEquals("bijian123he456", pinyin);

    }

    @Test
    public void testWithTone() {
        PinyinService service = CF.get(PinyinServiceWithTone.class);

        String str = "毕继安";
        String pinyin = service.toPinyin(str);
        System.out.println(str + ":" + pinyin);
        assertEquals("bi4ji4an1", pinyin);
        service = CF.get(PinyinServiceWithTone.class);
        str = "女";
        pinyin = service.toPinyin(str.charAt(0));
        System.out.println(str.charAt(0) + ":" + pinyin);
        assertEquals("nv3", pinyin);

        str = "和";
        String[] pinyins = service.toPinyins(str.charAt(0));
        System.out.println(str.charAt(0) + ":" + StringUtils.join(pinyins, ","));
        assertArrayEquals("he2,he4,huo2,huo4,huo5,hai1,he5,hu2".split(","), pinyins);

        str = "和";
        pinyin = service.toPinyin(str.charAt(0));
        System.out.println(str.charAt(0) + ":" + pinyin);
        assertEquals("he2", pinyin);

        str = "毕继安123和456";
        pinyin = service.toPinyin(str);
        System.out.println(str + ":" + pinyin);
        assertEquals("bi4ji4an1123he2456", pinyin);
    }

    @Test
    public void testWithTonePrint() {
        PinyinService service = CF.get(PinyinServiceWithTonePrint.class);
        String str = "毕继安";
        String pinyin = service.toPinyin(str);
        System.out.println(str + ":" + pinyin);
        assertEquals("bìjìān", pinyin);

        str = "女";
        pinyin = service.toPinyin(str.charAt(0));
        System.out.println(str.charAt(0) + ":" + pinyin);
        assertEquals("nǚ", pinyin);

        str = "和";
        String[] pinyins = service.toPinyins(str.charAt(0));
        System.out.println(str.charAt(0) + ":" + StringUtils.join(pinyins, ","));
        assertArrayEquals("hé,hè,huó,huò,huo,hāi,he,hú".split(","), pinyins);

        str = "和";
        pinyin = service.toPinyin(str.charAt(0));
        System.out.println(str.charAt(0) + ":" + pinyin);
        assertEquals("hé", pinyin);

        str = "毕继安123和456";
        pinyin = service.toPinyin(str);
        System.out.println(str + ":" + pinyin);
        assertEquals("bìjìān123hé456", pinyin);
    }

    @Test
    public void testPinyins1() {
        PinyinService service = CF.get(PinyinServiceBasic.class);

        String str1 = "毕继安";
        String[] pinyins1 = service.toPinyins(str1, -1);
        for (int i = 0; i < pinyins1.length; i++) {
            System.out.println(str1 + "(" + (i + 1) + "):" + pinyins1[i]);
        }
        System.out.println("限制两个");
        String str2 = "en，评书人，单田芳，最好，信我！";
        String[] pinyins2 = service.toPinyins(str2, 2);
        for (int i = 0; i < pinyins2.length; i++) {
            System.out.println(str2 + "(" + (i + 1) + "):" + pinyins2[i]);
        }
        System.out.println("无限制");

        String str3 = "en，评书人，单田芳，最好，信我！";
        String[] pinyins3 = service.toPinyins(str3, -1);
        for (int i = 0; i < pinyins3.length; i++) {
            System.out.println(str3 + "(" + (i + 1) + "):" + pinyins3[i]);
        }
    }

    @Test
    public void testPinyins2() {
        PinyinService service = CF.get(PinyinServiceWithTonePrint.class);

        String str1 = "毕继安";
        String[] pinyins1 = service.toPinyins(str1, -1);
        for (int i = 0; i < pinyins1.length; i++) {
            System.out.println(str1 + "(" + (i + 1) + "):" + pinyins1[i]);
        }

        String str2 = "en，评书人，单田芳，最好，信我！";
        String[] pinyins2 = service.toPinyins(str2, -1);
        for (int i = 0; i < pinyins2.length; i++) {
            System.out.println(str2 + "(" + (i + 1) + "):" + pinyins2[i]);
        }
    }
}