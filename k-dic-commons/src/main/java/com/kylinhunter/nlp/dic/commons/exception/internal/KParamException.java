package com.kylinhunter.nlp.dic.commons.exception.internal;

import com.kylinhunter.nlp.dic.commons.exception.KRuntimeException;
import com.kylinhunter.nlp.dic.commons.exception.ErrInfos;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022/1/1
 **/
public class KParamException extends KRuntimeException {
    private static final long serialVersionUID = 1L;

    public KParamException() {
    }

    public KParamException(String message) {
        super(ErrInfos.PARAM, message);
    }

    public KParamException(String message, Throwable cause) {
        super(ErrInfos.PARAM, message, cause);
    }
}
