package io.github.kylinhunter.dic.words.analyzer;

import io.github.kylinhunter.dic.words.analyzer.imp.IKWordAnalyzer;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description all hitWord analyzers
 * @date 2022-01-08 00:08
 **/
@AllArgsConstructor
public enum WordAnalyzerType {

    DEFAULT(IKWordAnalyzer.class),
    IK(IKWordAnalyzer.class);

    @Getter
    public Class<?> clazz;

}
