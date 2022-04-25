package com.kylinhunter.nlp.dic.core.config;

import com.kylinhunter.nlp.dic.core.loader.constants.DicType;

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
    private boolean assistMatch = true;
    private int wordMaxLen;
    private int skipMaxLen;
    private boolean defaultDicEnabled;
    private String exDic;

}
