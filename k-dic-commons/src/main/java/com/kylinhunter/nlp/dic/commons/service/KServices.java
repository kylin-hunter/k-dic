package com.kylinhunter.nlp.dic.commons.service;

import java.util.concurrent.atomic.AtomicInteger;

import com.kylinhunter.nlp.dic.commons.exception.internal.KInitException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description a simpler service factory
 * @date 2022/1/1
 **/
@Slf4j
public class KServices {
    private static final int MAX_SERVICE_NUMS = 1000; // max service nums supported
    private static final Object[] SERVICES = new Object[MAX_SERVICE_NUMS];
    private static final AtomicInteger SERVICE_ID_GENERATOR = new AtomicInteger(0);

    /**
     * @return int
     * @title get next service Id
     * @description
     * @author BiJi'an
     * @updateTime 2022/1/1 12:59
     */
    public static int register(KService<?> kService, Class<?> clazz) {
        int serviceId = kService.getServiceId();
        if (serviceId > 0) {
            throw new KInitException("register duplicate" + serviceId);

        }
        serviceId = SERVICE_ID_GENERATOR.incrementAndGet();
        if (serviceId >= MAX_SERVICE_NUMS) {
            throw new KInitException("exceed max limit " + MAX_SERVICE_NUMS);
        }
        kService.setServiceId(serviceId);

        try {
            if (clazz == null) {
                throw new KInitException("clazz can't be null");
            }
            SERVICES[serviceId] = clazz.newInstance();
        } catch (Exception e) {
            throw new KInitException("init SimpleService error", e);
        }
        return serviceId;
    }

    /**
     * @param kService kService
     * @return T
     * @title get a service
     * @description
     * @author BiJi'an
     * @updateTime 2022/1/1 1:00
     */
    @SuppressWarnings("unchecked")
    public static <T, R extends T> R get(KService<T> kService) {
        int serviceId = kService.getServiceId();
        return (R) SERVICES[serviceId];

    }

}
