package com.kylinhunter.nlp.dic.core.analyzer;

import com.kylinhunter.nlp.dic.core.analyzer.bean.Words;

/**
 * @description: word analyzer
 * @author: BiJi'an
 * @create: 2022/1/1
 **/
public interface WordAnalyzer {
    /**
     * @title analyze
     * @description 
     * @author BiJi'an 
     * @param text
     * @updateTime 2022-04-15 02:04
     * @return com.kylinhunter.nlp.Config.core.analyzer.bean.Words
     * @throws
     */
    Words analyze(String text);
}