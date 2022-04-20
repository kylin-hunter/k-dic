package com.kylinhunter.nlp.dic.core.config;

import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.kylinhunter.nlp.dic.commons.io.ResourceHelper;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2022-04-20 00:25
 **/
public class DicConfigParser {

    public static Config load() {
        Yaml yaml = new Yaml(new Constructor(Config.class));
        InputStream in = ResourceHelper.getInputStreamInClassPath("k-dic.yaml");
        Config config = yaml.load(in);

        return config;
    }

    public  static  void  loadAfter(Config config ){
        config.getDics().forEach((k, v) -> {
            v.setType(k);
        });
    }
}
