package com.kylinhunter.nlp.dic.core.dic.excel;

import com.kylinhunter.nlp.dic.core.dic.constants.DicType;
import com.kylinhunter.plat.commons.service.EServices;

/**
 * @author BiJi'an
 * @description
 * @create 2022-01-03 01:48
 **/
public class DicDataReaders {

    static {
        EServices.register(DicType.SENSITIVE, DicDataReaderForSensitive.class);
        EServices.register(DicType.COMPLETE, DicDataReaderForComplete.class);
        EServices.register(DicType.PINYIN, DicDataReaderForPinyin.class);
    }

    public static DicDataReader get(DicType dicType) {
        return EServices.get(dicType);
    }
}
