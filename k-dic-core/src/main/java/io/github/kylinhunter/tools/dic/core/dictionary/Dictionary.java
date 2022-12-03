package io.github.kylinhunter.tools.dic.core.dictionary;

import java.util.List;

import io.github.kylinhunter.tools.dic.core.dictionary.bean.MatchContext;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public interface Dictionary<T> {

    int getSkipMaxLen();

    int getMaxLength();

    int size();

    boolean put(String word, T t);

    boolean contains(String item);

    List<T> getValues(String item);

    T getValue(String item);

    /**
     * judge a part of text if a hitWord or not
     *
     * @param text   the text for judge
     * @param start  the start postion of text
     * @param length the length from start position
     */
    void match(char[] text, int start, int length, MatchContext<T> matchContext);

    void match(String text, MatchContext<T> matchContext);

}
