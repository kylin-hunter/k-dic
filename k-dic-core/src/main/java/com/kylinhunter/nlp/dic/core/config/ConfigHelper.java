package com.kylinhunter.nlp.dic.core.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.kylinhunter.nlp.dic.core.loader.constants.LoadSource;
import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.kylinhunter.nlp.dic.commons.exception.internal.KInitException;
import com.kylinhunter.nlp.dic.commons.io.ResourceHelper;
import com.kylinhunter.nlp.dic.commons.io.file.FileUtil;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 00:25
 **/
public class ConfigHelper {

    private static Config config;

    /**
     * @return com.kylinhunter.nlp.dic.core.config.Config
     * @title load
     * @description
     * @author BiJi'an
     * @updateTime 2022-04-21 00:22
     */
    public static Config get() {
        try {
            if (config == null) {
                synchronized (ConfigHelper.class) {
                    if (config == null) {
                        config = init();
                    }
                    return config;
                }
            } else {
                return config;
            }


        } catch (Exception e) {
            throw new KInitException("init dic config error", e);
        }

    }

    /*
     * @description  init
     * @date  2022/4/23 22:32
     * @author  BiJi'an
     * @Param
     * @return com.kylinhunter.nlp.dic.core.config.Config
     */
    public static Config init() {
        try {
            Yaml yaml = new Yaml(new Constructor(Config.class));
            InputStream in = ResourceHelper.getInputStreamInClassPath("k-dic.yaml");
            Config config = yaml.load(in);
            loadAfter(config);
            return config;
        } catch (Exception e) {
            throw new KInitException("init dic config error", e);
        }

    }

    /**
     * @param config config
     * @title loadAfter
     * @description
     * @author BiJi'an
     * @updateTime 2022-04-21 00:22
     */

    public static void loadAfter(Config config) {
        LoadConfig configLoad = config.getLoad();
        LoadConfigLocal loadConfigLocal = config.getLoad().getLocal();
        if (loadConfigLocal != null) {
            File exDicDir = FileUtil.correctPath(loadConfigLocal.getExDicDir());
            loadConfigLocal.setExDicDir(exDicDir.getAbsolutePath());
            if (!exDicDir.isDirectory()) {
                try {
                    FileUtils.forceMkdir(exDicDir);
                } catch (IOException e) {
                    throw new KInitException("mkdir error" + exDicDir, e);
                }
            }
            configLoad.setLoadSource(LoadSource.LOCAL);
        }
        config.getDics().forEach((k, v) -> v.setType(k));
    }
}
