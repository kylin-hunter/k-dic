package com.kylinhunter.nlp.dic.core.pinyin;

import com.kylinhunter.nlp.dic.commons.service.KService;
import com.kylinhunter.nlp.dic.commons.service.KServices;

import lombok.Getter;
import lombok.Setter;

public enum PinyinType implements KService<PinyinService> {

    BASIC(PinyinServices.PinyinServiceBasic.class),
    WITH_TONE(PinyinServices.PinyinServiceWithTone.class),
    WITH_TONE_PRINT(PinyinServices.PinyinServiceWithTonePrint.class);
    @Getter
    @Setter
    private Class<? extends PinyinService> clazz;
    @Setter
    @Getter
    int serviceId;

    PinyinType(Class<? extends PinyinService> clazz) {
        this.serviceId = KServices.nextServiceId();
        this.clazz = clazz;

    }
}
