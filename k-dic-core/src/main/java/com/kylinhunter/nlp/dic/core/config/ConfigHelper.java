package com.kylinhunter.nlp.dic.core.config;

import java.io.File;
import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.kylinhunter.nlp.dic.core.dic.constants.LoadSource;
import com.kylinhunter.plat.commons.exception.inner.InitException;
import com.kylinhunter.plat.commons.io.ResourceHelper;

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
     * @date 2022-01-21 00:22
     */
    public static Config get() {
        try {
            if (config == null) {
                synchronized(ConfigHelper.class) {
                    if (config == null) {
                        config = init();
                    }
                    return config;
                }
            } else {
                return config;
            }

        } catch (Exception e) {
            throw new InitException("init dic config error", e);
        }

    }

    /*
     * @description  init
     * @date  2022/1/23 22:32
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
            throw new InitException("init dic config error", e);
        }

    }

    /**
     * @param config config
     * @title loadAfter
     * @description
     * @author BiJi'an
     * @date 2022-01-21 00:22
     */

    public static void loadAfter(Config config) {
        LoadConfig loadConfig = config.getLoad();
        LoadConfigLocal loadConfigLocal = loadConfig.getLocal();
        if (loadConfigLocal != null && loadConfig.getSource() == LoadSource.LOCAL) {
             
            File dicPath = ResourceHelper.getFile(loadConfigLocal.getDicPath());
            if (dicPath != null && dicPath.exists()) {
                if (dicPath.isFile()) {
                    throw new InitException("dicPath  can't be a file :" + dicPath);
                }
            }

        }
        config.getDics().forEach((dicType, dicConfig) -> {
            dicConfig.setType(dicType);
            dicConfig.setDicMatchType(dicType.getDicMatchType());
        });
    }
}
