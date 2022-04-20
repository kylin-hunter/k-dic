package com.kylinhunter.nlp.dic.core.config;

import java.io.File;

import lombok.Data;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2022-01-01 23:54
 **/
@Data
public class LoadConfigLocal {
    private String exDicDir;
    private File exDicDirPath;
    private boolean autoScan;

}
