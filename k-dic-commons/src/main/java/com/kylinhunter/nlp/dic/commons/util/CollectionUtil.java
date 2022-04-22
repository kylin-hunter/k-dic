package com.kylinhunter.nlp.dic.commons.util;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022/1/1
 **/
@Slf4j
public class CollectionUtil {

    /**
     * @param results
     * @return java.util.List<T>
     * @throws
     * @title merge list
     * @description
     * @author BiJi'an
     * @updateTime 2022/1/1 8:30
     */
    @SafeVarargs
    public static <T> List<T> merge(List<T>... results) {

        List<T> resultAll = null;
        for (List<T> result : results) {
            if (result != null && result.size() > 0) {
                if (resultAll == null) {
                    resultAll = result;
                } else {
                    resultAll.addAll(result);
                }
            }
        }
        return resultAll;
    }
}
