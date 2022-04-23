package com.kylinhunter.nlp.dic.core.analyzer;

import com.kylinhunter.nlp.dic.core.analyzer.bean.Words;

/**
 * @description  word analyzer
 * @author  BiJi'an
 * @date 2022/1/1
 **/
public interface WordAnalyzer {
    /**
     * @title analyze
     * @description 
     * @author BiJi'an 
     * @param text text
     * @updateTime 2022-04-15 02:04
     * @return com.kylinhunter.nlp.Config.core.analyzer.bean.Words
     */
    Words analyze(String text);
}