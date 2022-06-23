package com.kylinhunter.nlp.dic.core.config;

import java.util.Map;

import com.kylinhunter.nlp.dic.core.words.analyzer.WordAnalyzerType;
import com.kylinhunter.nlp.dic.core.dictionary.DictionaryType;
import com.kylinhunter.nlp.dic.core.dic.constants.DicType;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 23:53
 **/
@Data
public class Config {
    private WordAnalyzerType wordAnalyzer = WordAnalyzerType.DEFAULT;
    private DictionaryType dictionaryType = DictionaryType.DEFAULT;
    private LoadConfig load;
    private Map<DicType, DicConfig> dics;

}
