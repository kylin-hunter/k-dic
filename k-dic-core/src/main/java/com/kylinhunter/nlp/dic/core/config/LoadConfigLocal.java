package com.kylinhunter.nlp.dic.core.config;

import com.kylinhunter.nlp.dic.commons.io.PathInfo;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 23:54
 **/
@Data
public class LoadConfigLocal {
    private String dicPath;
    private PathInfo dicPathInfo;
    private boolean autoScan;

    public void setDicPathInfo(PathInfo dicPathInfo) {
        this.dicPathInfo = dicPathInfo;
    }

}
