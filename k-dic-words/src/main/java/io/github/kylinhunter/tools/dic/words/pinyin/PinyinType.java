package io.github.kylinhunter.tools.dic.words.pinyin;

import io.github.kylinhunter.commons.component.CT;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PinyinType implements CT<PinyinService> {

    BASIC(PinyinServiceBasic.class),
    WITH_TONE(PinyinServiceWithTone.class),
    WITH_TONE_PRINT(PinyinServiceWithTonePrint.class);

    @Getter
    public final Class<? extends PinyinService> clazz;

}
