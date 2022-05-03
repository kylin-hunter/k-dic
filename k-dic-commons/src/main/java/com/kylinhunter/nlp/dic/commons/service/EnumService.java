package com.kylinhunter.nlp.dic.commons.service;

import com.kylinhunter.nlp.dic.commons.exception.internal.KInitException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @create 2022/5/3
 **/
@Slf4j
public class EnumService<R> {
    private final int MAX_SERVICE_NUMS = 20; // max service nums supported
    private final Object[] SERVICES = new Object[MAX_SERVICE_NUMS];

    /**
     * @param e e
     * @return R
     * @title get
     * @description
     * @author BiJi'an
     * @updateTime 2022-05-03 23:55
     */
    @SuppressWarnings("unchecked")
    public <T extends Enum<T>> R get(Enum<T> e) {
        return (R) SERVICES[e.ordinal()];
    }

    /**
     * @param e     e
     * @param clazz clazz
     * @return T
     * @title setService
     * @description
     * @author BiJi'an
     * @updateTime 2022-05-03 23:49
     */
    protected <T extends Enum<T>> R register(Enum<T> e, Class<? extends R> clazz) {

        try {
            if (clazz == null) {
                throw new KInitException("clazz can't be null");
            }
            R service = clazz.newInstance();
            SERVICES[e.ordinal()] = service;
            return service;
        } catch (Exception ex) {
            throw new KInitException("init SimpleService error", ex);
        }
    }

}
