package com.kylinhunter.nlp.dic.commons.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2022/1/1
 **/
@Getter
@Setter
public class KError extends Error implements ErrExplain {

    private static final long serialVersionUID = 1L;
    private ErrInfo errInfo = ErrInfos.UNKNOWN;
    private Object extra;

    public KError(String message, Throwable cause) {
        super(message, cause);
    }

    public KError(String message) {
        super(message);
    }

    public KError(Throwable cause) {
        super(cause);
    }

    public KError(ErrInfo errInfo, String message, Throwable cause) {
        super(message, cause);
        this.errInfo = errInfo;
    }

    public KError(ErrInfo errInfo, String message) {
        super(message);
        this.errInfo = errInfo;
    }

    public KError(ErrInfo errInfo, Throwable cause) {
        super(cause);
        this.errInfo = errInfo;
    }

}
