package com.kylinhunter.nlp.dic.commons.exception.internal;

import com.kylinhunter.nlp.dic.commons.exception.ErrInfos;
import com.kylinhunter.nlp.dic.commons.exception.KRuntimeException;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022/1/1
 **/
public class KFormatException extends KRuntimeException {
    private static final long serialVersionUID = 1L;

    public KFormatException() {
    }

    public KFormatException(String message) {
        super(ErrInfos.FORMAT, message);
    }

    public KFormatException(String message, Throwable e) {
        super(ErrInfos.FORMAT, message, e);
    }
}
