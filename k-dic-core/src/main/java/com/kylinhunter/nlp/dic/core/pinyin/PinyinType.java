package com.kylinhunter.nlp.dic.core.pinyin;

import com.kylinhunter.plat.commons.service.EService;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PinyinType implements EService {

    BASIC(PinyinServiceBasic.class),
    WITH_TONE(PinyinServiceWithTone.class),
    WITH_TONE_PRINT(PinyinServiceWithTonePrint.class);
    @Getter
    public Class<?> clazz;
}
