package io.github.kylinhunter.tools.dic.words.pinyin;

import io.github.kylinhunter.commons.component.C;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * @author BiJi'an
 * @description PinyinServiceWithTone
 * @date 2022/12/3
 **/
@C
public class PinyinServiceWithTone extends AbstractPinyinService {

    public PinyinServiceWithTone() {

        format.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

    }

}
