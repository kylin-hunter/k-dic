package com.kylinhunter.nlp.dic.core.match;

import com.kylinhunter.nlp.dic.commons.service.KService;
import com.kylinhunter.nlp.dic.commons.service.KServices;
import com.kylinhunter.nlp.dic.core.match.imp.DicMatchFull;

import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description all word analyzers
 * @date 2022-04-08 00:08
 **/
public enum DicMatchType implements KService<DicMatch> {

    DEFAULT(DicMatchFull.class),
    FULL(DicMatchFull.class);
    @Getter
    @Setter
    Class<? extends DicMatch> clazz;
    @Setter
    @Getter
    int serviceId;

    DicMatchType(Class<? extends DicMatch> clazz) {
        this.serviceId = KServices.nextServiceId();
        this.clazz = clazz;

    }
}
