package io.github.kylinhunter.tools.dic.app.config;

import io.github.kylinhunter.tools.dic.app.constants.DicType;
import io.github.kylinhunter.tools.dic.core.match.DictionaryMatcherType;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 23:54
 **/
@Data
public class DicConfig {
    private DicType type;
    private DictionaryMatcherType dictionaryMatcherType;
    private boolean assistMatchEnabled = true;
    private int wordMaxLen = 10;
    private int unclearSkipMaxLen = 2;
    private boolean defaultDicEnabled = false;
    private String dic;

}
