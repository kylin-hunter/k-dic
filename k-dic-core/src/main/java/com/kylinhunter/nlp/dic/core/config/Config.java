package com.kylinhunter.nlp.dic.core.config;

import java.util.Map;

import com.kylinhunter.nlp.dic.core.analyzer.WordAnalyzerType;
import com.kylinhunter.nlp.dic.core.loader.constants.DicType;

import lombok.Data;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022-01-01 23:53
 **/
@Data
public class Config {
    private WordAnalyzerType wordAnalyzer = WordAnalyzerType.DEFAULT;
    private LoadConfig load;
    private Map<DicType, DicConfig> dics;

}
