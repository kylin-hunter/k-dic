package io.github.kylinhunter.tools.dic.words.pinyin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PinyinType {

    BASIC(PinyinServiceBasic.class),
    WITH_TONE(PinyinServiceWithTone.class),
    WITH_TONE_PRINT(PinyinServiceWithTonePrint.class);
    @Getter
    public Class<? extends PinyinService> clazz;
}
