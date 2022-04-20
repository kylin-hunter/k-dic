package com.kylinhunter.nlp.dic.core.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.kylinhunter.nlp.dic.commons.exception.internal.KInitException;
import com.kylinhunter.nlp.dic.commons.io.ResourceHelper;
import com.kylinhunter.nlp.dic.commons.io.file.FileUtil;
import com.kylinhunter.nlp.dic.commons.io.file.UserDirUtils;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2022-01-01 00:25
 **/
public class ConfigHelper {

    /**
     * @return com.kylinhunter.nlp.dic.core.config.Config
     * @throws
     * @title load
     * @description
     * @author BiJi'an
     * @updateTime 2022-04-21 00:22
     */
    public static Config load() {
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
     * @param config
     * @return void
     * @throws
     * @title loadAfter
     * @description
     * @author BiJi'an
     * @updateTime 2022-04-21 00:22
     */

    public static void loadAfter(Config config) {
        LoadConfigLocal loadConfigLocal = config.getLoad().getLocal();
        loadConfigLocal.setExDicDir(FileUtil.correctPath(loadConfigLocal.getExDicDir()));
        String exDicDir = loadConfigLocal.getExDicDir();
        File exDicDirPath = new File(exDicDir);
        if (!exDicDirPath.isDirectory()) {
            try {
                FileUtils.forceMkdir(exDicDirPath);
            } catch (IOException e) {
                throw new KInitException("mkdir error" + exDicDir, e);
            }
        }

        config.getDics().forEach((k, v) -> {
            v.setType(k);

        });
    }
}
