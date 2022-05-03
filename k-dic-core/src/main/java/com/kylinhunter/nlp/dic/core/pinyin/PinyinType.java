package com.kylinhunter.nlp.dic.core.pinyin;

import com.kylinhunter.nlp.dic.commons.service.EnumService;
import com.kylinhunter.nlp.dic.commons.service.KService;
import com.kylinhunter.nlp.dic.commons.service.KServices;

import lombok.Getter;
import lombok.Setter;

public enum PinyinType implements KService<PinyinService> {

    BASIC(PinyinServiceBasic.class),
    WITH_TONE(PinyinServiceWithTone.class),
    WITH_TONE_PRINT(PinyinServiceWithTonePrint.class);
    private static EnumService<?> enumService = new EnumService<>();

    @Setter
    @Getter
    int serviceId;



    PinyinType(Class<? extends PinyinService> clazz) {
        KServices.register(this,clazz);

    }
}
