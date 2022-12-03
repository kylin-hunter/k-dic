package io.github.kylinhunter.tools.dic.words.analyzer;

import io.github.kylinhunter.commons.component.CT;
import io.github.kylinhunter.tools.dic.words.analyzer.imp.IKWordAnalyzer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description all hitWord analyzers
 * @date 2022-01-08 00:08
 **/
@RequiredArgsConstructor
public enum WordAnalyzerType  implements CT<WordAnalyzer> {

    DEFAULT(IKWordAnalyzer.class),
    IK(IKWordAnalyzer.class);

    @Getter
    public final Class<? extends WordAnalyzer> clazz;

}
