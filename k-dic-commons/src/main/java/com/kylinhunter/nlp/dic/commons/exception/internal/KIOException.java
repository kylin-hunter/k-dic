package com.kylinhunter.nlp.dic.commons.exception.internal;

import com.kylinhunter.nlp.dic.commons.exception.ErrInfos;
import com.kylinhunter.nlp.dic.commons.exception.KRuntimeException;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022/1/1
 **/
public class KIOException extends KRuntimeException {
    private static final long serialVersionUID = 1L;

    public KIOException() {
    }

    public KIOException(String message) {
        super(ErrInfos.IO, message);
    }

    public KIOException(String message, Throwable e) {
        super(ErrInfos.IO, message, e);
    }
}
