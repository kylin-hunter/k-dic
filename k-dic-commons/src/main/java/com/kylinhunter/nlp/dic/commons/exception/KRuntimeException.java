package com.kylinhunter.nlp.dic.commons.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022/1/1
 **/
@Getter
@Setter
public class KRuntimeException extends RuntimeException implements ErrExplain {
    private static final long serialVersionUID = 1L;
    private ErrInfo errInfo = ErrInfos.UNKNOWN;
    private Object extra;

    public KRuntimeException() {
        super();
    }

    public KRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public KRuntimeException(String message) {
        super(message);
    }

    public KRuntimeException(Throwable cause) {
        super(cause);
    }

    public KRuntimeException(ErrInfo errInfo, String message, Throwable cause) {
        super(message, cause);
        this.errInfo = errInfo;
    }

    public KRuntimeException(ErrInfo errInfo, String message) {
        super(message);
        this.errInfo = errInfo;
    }

    public KRuntimeException(ErrInfo errInfo, Throwable cause) {
        super(cause);
        this.errInfo = errInfo;
    }

}
