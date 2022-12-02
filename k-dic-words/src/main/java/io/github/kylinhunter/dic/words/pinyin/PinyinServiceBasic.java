package io.github.kylinhunter.dic.words.pinyin;

import io.github.kylinhunter.commons.component.C;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * @author BiJi'an
 * @description PinyinServiceBasic
 * @create 2022/1/1
 **/
@C
public class PinyinServiceBasic extends AbstractPinyinService {
    public PinyinServiceBasic() {
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

}
