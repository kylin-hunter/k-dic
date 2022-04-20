package com.kylinhunter.nlp.dic.core.config;

import com.kylinhunter.nlp.dic.core.loader.constants.DicType;

import lombok.Data;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2022-01-01 23:54
 **/
@Data
public class DicConfig {
    private DicType type;
    private int wordMaxLen;
    private boolean defaultDicEnabled;
    private String exDic;


}
