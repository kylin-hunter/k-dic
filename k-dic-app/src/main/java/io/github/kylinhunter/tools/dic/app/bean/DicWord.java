package io.github.kylinhunter.tools.dic.app.bean;

import io.github.kylinhunter.tools.dic.core.dictionary.WordNodeAware;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-05 00:15
 **/
public interface DicWord extends WordNodeAware {
    void setWord(String word);

    String getWord();
}
