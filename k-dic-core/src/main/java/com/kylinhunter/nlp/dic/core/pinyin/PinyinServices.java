package com.kylinhunter.nlp.dic.core.pinyin;

import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

public class PinyinServices {

    /**
     * @description: 基础的拼音服务
     * @author: BiJi'an
     * @create: 2022/1/1
     **/
    public static class PinyinServiceBasic extends AbstractPinyinService {
        public PinyinServiceBasic() {
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            format.setVCharType(HanyuPinyinVCharType.WITH_V);
        }

    }

    /**
     * @description: 拼音带声调
     * @author: BiJi'an
     * @create: 2022/1/1
     **/

    public static class PinyinServiceWithTone extends AbstractPinyinService {

        public PinyinServiceWithTone() {

            format.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
            format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            format.setVCharType(HanyuPinyinVCharType.WITH_V);

        }

    }

    /**
     * @description: 拼音带声调打印体
     * @author: BiJi'an
     * @create: 2022/1/1
     **/
    public static class PinyinServiceWithTonePrint extends AbstractPinyinService {

        public PinyinServiceWithTonePrint() {
            format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
            format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);

        }

    }

}
