package com.kylinhunter.nlp.dic.commons.exception.internal;

import com.kylinhunter.nlp.dic.commons.exception.ErrInfos;
import com.kylinhunter.nlp.dic.commons.exception.KRuntimeException;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022/1/1
 **/
public class KInitException extends KRuntimeException {

    private static final long serialVersionUID = 1L;

    public KInitException() {
    }

    public KInitException(String message, Throwable cause) {
        super(ErrInfos.INIT, message, cause);
    }

    public KInitException(String message) {
        super(ErrInfos.INIT, message);
    }

    public KInitException(Throwable cause) {
        super(ErrInfos.INIT, cause);
    }

}
