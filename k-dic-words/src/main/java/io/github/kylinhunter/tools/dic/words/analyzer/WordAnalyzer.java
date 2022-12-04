package io.github.kylinhunter.tools.dic.words.analyzer;

import io.github.kylinhunter.tools.dic.words.analyzer.bean.Words;

/**
 * @description  hitWord wordAnalyzer
 * @author  BiJi'an
 * @date 2022/1/1
 **/
public interface WordAnalyzer {
    /**
     * @title analyze
     * @description 
     * @author BiJi'an 
     * @param text text
     * @date 2022-01-15 02:04
     * @return io.github.kylinhunter.toolsConfig.core.wordAnalyzer.bean.Words
     */
    Words analyze(String text);
}