package com.kylinhunter.nlp.dic.core.config;

import lombok.Data;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2022-01-01 23:54
 **/
@Data
public class DicDataSourceLocal {
    private String exDicDir;
    private boolean autoScan;
}
