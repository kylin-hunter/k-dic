package io.github.kylinhunter.tools.dic.core.dictionary;

import io.github.kylinhunter.tools.dic.core.dictionary.bean.WordNode;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.HitMode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
@Slf4j
@Getter
@Setter
public class DictionaryGroup<T extends WordNode> {

    private Dictionary<T> high;
    private Dictionary<T> middle;
    private Dictionary<T> low;
    private Dictionary<T> highMiddleLow;
    private Dictionary<T> middleLow;

    public DictionaryGroup() {
        this(2);
    }

    public DictionaryGroup(int skipMaxLen) {
        high = new Dictionary<>(skipMaxLen);
        middle = new Dictionary<>(skipMaxLen);
        low = new Dictionary<>(skipMaxLen);
        highMiddleLow = new Dictionary<>(skipMaxLen);
        middleLow = new Dictionary<>(skipMaxLen);
    }

    /**
     * @param wordNode wordNode
     * @return void
     * @title put
     * @description
     * @author BiJi'an
     * @date 2022-12-04 01:43
     */
    public void put(T wordNode) {
        HitMode hitMode = wordNode.getHitMode();
        if (hitMode == HitMode.HIGH) {
            high.put(wordNode.getWord(), wordNode);
            highMiddleLow.put(wordNode.getWord(), wordNode);

        } else if (hitMode == HitMode.MIDDLE) {
            middle.put(wordNode.getWord(), wordNode);
            highMiddleLow.put(wordNode.getWord(), wordNode);
            middleLow.put(wordNode.getWord(), wordNode);

        } else if (hitMode == HitMode.LOW) {
            low.put(wordNode.getWord(), wordNode);
            highMiddleLow.put(wordNode.getWord(), wordNode);
            middleLow.put(wordNode.getWord(), wordNode);
        }

    }

}
