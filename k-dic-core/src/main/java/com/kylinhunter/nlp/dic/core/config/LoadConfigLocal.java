package com.kylinhunter.nlp.dic.core.config;

import java.io.File;

import lombok.Data;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022-01-01 23:54
 **/
@Data
public class LoadConfigLocal {
    private String dicPath;
    private File exDicDirPath;
    private boolean autoScan;

}
