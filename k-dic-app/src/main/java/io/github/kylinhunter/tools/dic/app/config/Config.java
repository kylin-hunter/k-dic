package io.github.kylinhunter.tools.dic.app.config;

import java.util.Map;

import io.github.kylinhunter.tools.dic.app.constants.DicType;

import io.github.kylinhunter.tools.dic.words.analyzer.WordAnalyzerType;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 23:53
 **/
@Data
public class Config {
    private WordAnalyzerType wordAnalyzer = WordAnalyzerType.DEFAULT;
    private LoadConfig load;
    private Map<DicType, DicConfig> dics;

}
