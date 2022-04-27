package com.kylinhunter.nlp.dic.core.config;

import com.kylinhunter.nlp.dic.core.loader.constants.DicType;
import com.kylinhunter.nlp.dic.core.match.DicMatchType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 23:54
 **/
@Data
public class DicConfig {
    private DicType type;
    private DicMatchType dicMatchType;
    private boolean assistMatch = true;
    private int wordMaxLen = 10;
    private int skipMaxLen = 2;
    private int prefixMatchMaxNum = 10;
    private boolean defaultDicEnabled;
    private String exDic;

}
