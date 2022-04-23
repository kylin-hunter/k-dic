package com.kylinhunter.nlp.dic.core.analyzer;


import com.kylinhunter.nlp.dic.commons.service.KService;
import com.kylinhunter.nlp.dic.commons.service.KServices;
import com.kylinhunter.nlp.dic.core.analyzer.imp.IKWordAnalyzer;

import lombok.Getter;
import lombok.Setter;

/**
 * @description  all word analyzers
 * @author  BiJi'an
 * @date 2022-04-08 00:08
 **/
public enum WordAnalyzerType implements KService<WordAnalyzer> {

    DEFAULT(IKWordAnalyzer.class),
    IK(IKWordAnalyzer.class);

    @Getter
    @Setter
    Class<? extends WordAnalyzer> clazz;
    @Setter
    @Getter
    int serviceId;

    WordAnalyzerType(Class<? extends WordAnalyzer> clazz) {
        this.serviceId = KServices.nextServiceId();
        this.clazz = clazz;

    }
}
