package com.kylinhunter.nlp.dic.core.dic.bean;

import java.io.Serializable;

import com.kylinhunter.nlp.dic.core.dictionary.group.bean.HitMode;

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
