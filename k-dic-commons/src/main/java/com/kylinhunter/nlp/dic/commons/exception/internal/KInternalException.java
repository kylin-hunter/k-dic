package com.kylinhunter.nlp.dic.commons.exception.internal;

import com.kylinhunter.nlp.dic.commons.exception.ErrInfos;
import com.kylinhunter.nlp.dic.commons.exception.KRuntimeException;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022/1/1
 **/
public class KInternalException extends KRuntimeException {
    private static final long serialVersionUID = 1L;

    public KInternalException() {
    }

    public KInternalException(String message, Throwable cause) {
        super(ErrInfos.INTERNAL, message, cause);

    }

    public KInternalException(String message) {
        super(ErrInfos.INTERNAL, message);

    }

    public KInternalException(Throwable cause) {
        super(ErrInfos.INTERNAL, cause);

    }

}
