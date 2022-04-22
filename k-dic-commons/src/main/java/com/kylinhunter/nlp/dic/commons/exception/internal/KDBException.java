package com.kylinhunter.nlp.dic.commons.exception.internal;

import com.kylinhunter.nlp.dic.commons.exception.ErrInfos;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022/1/1
 **/
public class KDBException extends KBizException {
    private static final long serialVersionUID = 1L;

    public KDBException() {
    }

    public KDBException(String message) {
        super(ErrInfos.DB_ERROR, message);
    }

    public KDBException(String message, Throwable cause) {
        super(ErrInfos.DB_ERROR, message, cause);
    }

}
