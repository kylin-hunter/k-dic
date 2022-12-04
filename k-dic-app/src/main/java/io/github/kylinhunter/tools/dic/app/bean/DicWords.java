package io.github.kylinhunter.tools.dic.app.bean;

import io.github.kylinhunter.tools.dic.core.dictionary.constant.HitMode;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-05 00:49
 **/
public class DicWords {

    /**
     * @param keyword word
     * @return io.github.kylinhunter.tools.dic.app.bean.DicWordSensitive
     * @title createDicWordSensitive
     * @description
     * @author BiJi'an
     * @date 2022-12-05 00:52
     */
    public static DicWordSensitive createSensitive(HitMode hitMode, String keyword) {
        return new DicWordSensitiveImp(hitMode, keyword);
    }

    public static DicWordSensitive createSensitive(HitMode hitMode, String keyword, String[] assistedKeywords) {
        return new DicWordSensitiveImp(hitMode, keyword, assistedKeywords);
    }

}
