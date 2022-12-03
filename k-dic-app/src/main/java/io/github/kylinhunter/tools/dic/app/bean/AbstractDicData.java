package io.github.kylinhunter.tools.dic.app.bean;

import java.io.Serializable;

import io.github.kylinhunter.tools.dic.core.dictionary.constant.HitMode;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
@Data
public abstract class AbstractDicData implements Serializable {
    private HitMode hitMode;
    private int type;

}
