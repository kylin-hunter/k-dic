package com.kylinhunter.nlp.dic.commons.service;

/**
 * @author BiJi'an
 * @description
 * @create 2022-05-04 00:19
 **/
public class EnumServiceBuilder<R> {
    private final EnumService<R> enumService = new EnumService<>();

    public <T extends Enum<T>> EnumServiceBuilder<R> register(Enum<T> e, Class<? extends R> clazz) {
        enumService.register(e, clazz);
        return this;
    }

    public EnumService<R> build() {
        return enumService;
    }
}
