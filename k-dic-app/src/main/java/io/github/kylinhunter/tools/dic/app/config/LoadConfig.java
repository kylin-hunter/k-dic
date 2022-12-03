package io.github.kylinhunter.tools.dic.app.config;

import io.github.kylinhunter.tools.dic.app.constants.LoadSource;
import lombok.Data;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022-01-01 23:54
 **/
@Data
public class LoadConfig {
    private LoadConfigLocal local;
    private LoadSource source;
}
