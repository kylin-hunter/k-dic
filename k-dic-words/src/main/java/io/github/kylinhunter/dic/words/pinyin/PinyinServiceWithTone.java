package io.github.kylinhunter.dic.words.pinyin;

import io.github.kylinhunter.commons.component.C;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * @author BiJi'an
 * @description PinyinServiceWithTone
 * @create 2022/1/1
 **/

@C
public class PinyinServiceWithTone extends AbstractPinyinService {

    public PinyinServiceWithTone() {

        format.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

    }

}
