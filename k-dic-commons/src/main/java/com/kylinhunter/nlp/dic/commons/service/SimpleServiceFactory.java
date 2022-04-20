package com.kylinhunter.nlp.dic.commons.service;

import java.util.concurrent.atomic.AtomicInteger;

import com.kylinhunter.nlp.dic.commons.exception.internal.KInitException;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: a simpler service factory
 * @author: BiJi'an
 * @create: 2022/1/1
 **/
@Slf4j
public class SimpleServiceFactory {
    private static final int MAX_SERVICE_NUMS = 1000; // max service nums supported
    private static Object[] SERVICES = new Object[MAX_SERVICE_NUMS];
    private static final AtomicInteger SERVICE_ID_GENERATOR = new AtomicInteger(0);

    /**
     * @return int
     * @throws
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
     * @param simpleService
     * @return T
     * @throws
     * @title get a service
     * @description
     * @author BiJi'an
     * @updateTime 2022/1/1 1:00
     */
    public static <T, R extends T> R get(SimpleService<T> simpleService) {
        int serviceId = simpleService.getServiceId();
        R service = (R) SERVICES[serviceId];
        if (service != null) {
            return service;
        } else {
            return (R) init(simpleService);
        }

    }

    /**
     * @param simpleService
     * @return T
     * @throws
     * @title init service
     * @description
     * @author BiJi'an
     * @updateTime 2022/1/1 1:07
     */

    private static <T> T init(SimpleService<T> simpleService) {

        int serviceId = simpleService.getServiceId();
        synchronized(SimpleServiceFactory.class) {
            T service = (T) SERVICES[serviceId];
            if (service != null) {
                return service;
            } else {
                return setService(serviceId, simpleService.getClazz());
            }
        }

    }

    /**
     * @param serviceId
     * @param clazz
     * @return T
     * @throws
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

    /**
     * @param simpleService
     * @return T
     * @throws
     * @title create
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 01:11
     */
    public static <T, R extends T> R create(SimpleService<T> simpleService) {
        try {
            Class<? extends T> clazz = simpleService.getClazz();
            if (clazz == null) {
                throw new KInitException("clazz can't be null");
            }
            return (R) clazz.newInstance();
        } catch (Exception e) {
            throw new KInitException(" create service error", e);
        }
    }

}
