package com.kylinhunter.nlp.dic.commons.exception.internal;

import com.kylinhunter.nlp.dic.commons.exception.ErrInfo;
import com.kylinhunter.nlp.dic.commons.exception.ErrInfos;
import com.kylinhunter.nlp.dic.commons.exception.KRuntimeException;

/**
 * @description
 * @author  BiJi'an
 * @date 2022/1/1
 **/
public class KBizException extends KRuntimeException {

    private static final long serialVersionUID = 1L;

    public KBizException() {
    }

    public KBizException(String message, Throwable cause) {
        this(ErrInfos.BIZ_ERROR, message, cause);
    }

    public KBizException(String message) {
        this(ErrInfos.BIZ_ERROR, message);
    }

    public KBizException(Throwable cause) {
        this(ErrInfos.BIZ_ERROR, cause);
    }

    public KBizException(ErrInfo errInfo, String message, Throwable cause) {
        super(errInfo, message, cause);
    }

    public KBizException(ErrInfo errInfo, String message) {
        super(errInfo, message);
    }

    public KBizException(ErrInfo errInfo, Object extra) {
        super(errInfo, "");
        this.setExtra(extra);
    }

    public KBizException(ErrInfo errInfo, Throwable cause) {
        super(errInfo, cause);
    }

}
