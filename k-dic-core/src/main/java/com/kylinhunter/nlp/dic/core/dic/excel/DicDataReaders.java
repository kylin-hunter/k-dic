package com.kylinhunter.nlp.dic.core.dic.excel;

import com.kylinhunter.nlp.dic.commons.service.EnumService;
import com.kylinhunter.nlp.dic.commons.service.EnumServiceBuilder;
import com.kylinhunter.nlp.dic.core.dic.constants.DicType;

/**
 * @author BiJi'an
 * @description
 * @create 2022-01-03 01:48
 **/
public class DicDataReaders {
    private static EnumService<DicDataReader> enumService = new EnumServiceBuilder<DicDataReader>()
            .register(DicType.SENSITIVE, DicDataReaderForSensitive.class)
            .register(DicType.COMPLETE, DicDataReaderForComplete.class)
            .register(DicType.PINYIN, DicDataReaderForPinyin.class)
            .build();

    public static DicDataReader get(DicType dicType) {
        return enumService.get(dicType);
    }
}
