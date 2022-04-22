package com.kylinhunter.nlp.dic.commons.service;

/**
 * @description  a definition for simple service
 * @author  BiJi'an
 * @date 2022/1/1
 **/
public interface SimpleService<T> {

    Class<? extends T> getClazz();

    int getServiceId();

}
