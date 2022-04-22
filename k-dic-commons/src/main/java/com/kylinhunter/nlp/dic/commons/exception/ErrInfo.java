package com.kylinhunter.nlp.dic.commons.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022/1/1
 **/
@Data
@AllArgsConstructor
public class ErrInfo {
    private int code;
    private String defaultMsg;

    public ErrInfo(int code) {
        this.code = code;
    }
}
