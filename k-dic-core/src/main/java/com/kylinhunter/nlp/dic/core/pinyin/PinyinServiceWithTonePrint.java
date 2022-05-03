package com.kylinhunter.nlp.dic.core.pinyin;

import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * @author BiJi'an
 * @description PinyinServiceWithTonePrint
 * @create 2022/1/1
 **/
public class PinyinServiceWithTonePrint extends AbstractPinyinService {

    public PinyinServiceWithTonePrint() {
        format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);

    }

}
