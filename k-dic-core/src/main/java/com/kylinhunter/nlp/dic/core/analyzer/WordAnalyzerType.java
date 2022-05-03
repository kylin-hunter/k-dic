package com.kylinhunter.nlp.dic.core.analyzer;

import com.kylinhunter.nlp.dic.commons.service.KService;
import com.kylinhunter.nlp.dic.commons.service.KServices;
import com.kylinhunter.nlp.dic.core.analyzer.imp.IKWordAnalyzer;

import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description all hitWord analyzers
 * @date 2022-01-08 00:08
 **/
public enum WordAnalyzerType implements KService<WordAnalyzer> {

    DEFAULT(IKWordAnalyzer.class),
    IK(IKWordAnalyzer.class);

    @Setter
    @Getter
    int serviceId;

    WordAnalyzerType(Class<? extends WordAnalyzer> clazz) {
        KServices.register(this, clazz);
    }
}
