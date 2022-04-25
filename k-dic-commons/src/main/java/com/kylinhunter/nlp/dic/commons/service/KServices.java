package com.kylinhunter.nlp.dic.commons.service;

import java.lang.reflect.Constructor;
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
    public static int nextServiceId() {
        int serviceId = SERVICE_ID_GENERATOR.incrementAndGet();
        if (serviceId >= MAX_SERVICE_NUMS) {
            throw new KInitException("exceed max limit " + MAX_SERVICE_NUMS);
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
        R service = (R) SERVICES[serviceId];
        if (service != null) {
            return service;
        } else {
            return (R) init(kService);
        }

    }

    /**
     * @param kService kService
     * @return T
     * @title init service
     * @description
     * @author BiJi'an
     * @updateTime 2022/1/1 1:07
     */

    @SuppressWarnings("unchecked")
    private static <T> T init(KService<T> kService) {

        int serviceId = kService.getServiceId();
        synchronized(KServices.class) {
            T service = (T) SERVICES[serviceId];
            if (service != null) {
                return service;
            } else {
                return setService(serviceId, kService.getClazz());
            }
        }

    }

    /**
     * @param serviceId serviceId
     * @param clazz     clazz
     * @return T
     * @title set a service
     * @description
     * @author BiJi'an
     * @updateTime 2022/1/1 1:06
     */
    private static <T> T setService(int serviceId, Class<? extends T> clazz) {

        try {
            if (clazz == null) {
                throw new KInitException("clazz can't be null");
            }
            T service = clazz.newInstance();
            SERVICES[serviceId] = service;
            return service;
        } catch (Exception e) {
            throw new KInitException("init SimpleService error", e);
        }
    }

}
