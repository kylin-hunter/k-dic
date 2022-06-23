package com.kylinhunter.nlp.dic.core.words.analyzer;

import com.kylinhunter.nlp.dic.core.words.analyzer.imp.IKWordAnalyzer;
import com.kylinhunter.plat.commons.service.EService;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description all hitWord analyzers
 * @date 2022-01-08 00:08
 **/
@AllArgsConstructor
public enum WordAnalyzerType implements EService {

    DEFAULT(IKWordAnalyzer.class),
    IK(IKWordAnalyzer.class);

    @Getter
    public Class<?> clazz;

}
