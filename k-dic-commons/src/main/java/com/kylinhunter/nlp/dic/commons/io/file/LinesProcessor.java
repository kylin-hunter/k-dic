package com.kylinhunter.nlp.dic.commons.io.file;

/**
 * @description: line processor
 * @author: BiJi'an
 * @create: 2022/1/1
 **/
public interface LinesProcessor<T> {
    void process(String line);

}
