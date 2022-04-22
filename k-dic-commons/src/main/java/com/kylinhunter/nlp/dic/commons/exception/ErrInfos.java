package com.kylinhunter.nlp.dic.commons.exception;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022/1/1
 **/
public class ErrInfos {
    public static final int SUCCESS = 0;
    public static final String MSG_UNKNOWN = "UNKNOWN";
    public static final ErrInfo UNKNOWN = new ErrInfo(-1);  /*UNKNOWN*/
    public static final ErrInfo IO = new ErrInfo(10000);
    public static final ErrInfo INIT = new ErrInfo(10001);
    public static final ErrInfo INTERNAL = new ErrInfo(10002);
    public static final ErrInfo FORMAT = new ErrInfo(10003);
    public static final ErrInfo PARAM = new ErrInfo(10004);
    public static final ErrInfo BIZ_ERROR = new ErrInfo(10005);
    public static final ErrInfo DB_ERROR = new ErrInfo(10006);

}
