package io.github.kylinhunter.dic.words.pinyin;

import io.github.kylinhunter.commons.component.C;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * @author BiJi'an
 * @description PinyinServiceWithTonePrint
 * @date 2022/12/3
 **/
@C
public class PinyinServiceWithTonePrint extends AbstractPinyinService {

    public PinyinServiceWithTonePrint() {
        format.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);

    }

}
