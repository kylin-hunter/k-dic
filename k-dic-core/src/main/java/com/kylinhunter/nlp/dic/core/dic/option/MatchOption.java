package com.kylinhunter.nlp.dic.core.dic.option;


import com.kylinhunter.nlp.dic.core.dic.constant.FindType;
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022-01-01 15:10
 **/
@AllArgsConstructor
@Data
public class MatchOption {
    private FindType findType;
    private FindLevel findLevel;

}
