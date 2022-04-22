package com.kylinhunter.nlp.dic.core.config;

import com.kylinhunter.nlp.dic.core.loader.constants.LoadSource;
import lombok.Data;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2022-01-01 23:54
 **/
@Data
public class LoadConfig {
    private LoadConfigLocal local;
    private LoadSource loadSource;
}
