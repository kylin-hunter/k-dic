package io.github.kylinhunter.dic.words.analyzer;

import io.github.kylinhunter.dic.words.analyzer.bean.Words;

/**
 * @description  hitWord analyzer
 * @author  BiJi'an
 * @date 2022/1/1
 **/
public interface WordAnalyzer {
    /**
     * @title analyze
     * @description 
     * @author BiJi'an 
     * @param text text
     * @updateTime 2022-01-15 02:04
     * @return com.kylinhunter.nlp.Config.core.analyzer.bean.Words
     */
    Words analyze(String text);
}