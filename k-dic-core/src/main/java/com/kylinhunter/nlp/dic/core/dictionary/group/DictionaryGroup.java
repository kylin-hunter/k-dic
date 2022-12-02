package com.kylinhunter.nlp.dic.core.dictionary.group;

import com.kylinhunter.nlp.dic.core.config.Config;
import com.kylinhunter.nlp.dic.core.config.ConfigHelper;
import com.kylinhunter.nlp.dic.core.config.DicConfig;
import com.kylinhunter.nlp.dic.core.dictionary.Dictionary;
import com.kylinhunter.nlp.dic.core.dictionary.DictionaryCreator;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.HitMode;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.DictionaryNode;

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
public class DictionaryGroup<T extends DictionaryNode> {
    private final Config config = ConfigHelper.get();
    private DicConfig dicConfig;
    private Dictionary<T> high;
    private Dictionary<T> middle;
    private Dictionary<T> low;
    private Dictionary<T> highMiddleLow;
    private Dictionary<T> middleLow;

    public DictionaryGroup(DicConfig dicConfig) {
        this.dicConfig = dicConfig;
        high = DictionaryCreator.create(config.getDictionaryType(), dicConfig.getUnclearSkipMaxLen());
        middle = DictionaryCreator.create(config.getDictionaryType(), dicConfig.getUnclearSkipMaxLen());
        low = DictionaryCreator.create(config.getDictionaryType(), dicConfig.getUnclearSkipMaxLen());
        highMiddleLow = DictionaryCreator.create(config.getDictionaryType(), dicConfig.getUnclearSkipMaxLen());
        middleLow = DictionaryCreator.create(config.getDictionaryType(), dicConfig.getUnclearSkipMaxLen());
    }

    /**
     * @title put
     * @description
     * @author BiJi'an
     * @date 2022-01-01 03:04
     */
    public void put(T wordNode) {
        HitMode hitMode = wordNode.getHitMode();
        if (hitMode == HitMode.HIGH) {
            high.put(wordNode.getKeyword(), wordNode);
            highMiddleLow.put(wordNode.getKeyword(), wordNode);
            log.info("add hitWord:" + wordNode);

        } else if (hitMode == HitMode.MIDDLE) {
            middle.put(wordNode.getKeyword(), wordNode);
            highMiddleLow.put(wordNode.getKeyword(), wordNode);
            middleLow.put(wordNode.getKeyword(), wordNode);
            log.info("add hitWord:" + wordNode);

        } else if (hitMode == HitMode.LOW) {
            low.put(wordNode.getKeyword(), wordNode);
            highMiddleLow.put(wordNode.getKeyword(), wordNode);
            middleLow.put(wordNode.getKeyword(), wordNode);
            log.info("add hitWord:" + wordNode);
        }

    }
}
