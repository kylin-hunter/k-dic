package com.kylinhunter.nlp.dic.commons.io.file;

/**
 * @description  default line processor
 * @author  BiJi'an
 * @date 2022/1/1
 **/
public abstract class DefaultLinesProcessor<T> implements LinesProcessor {

    T getResult() {
        return null;
    }
}
