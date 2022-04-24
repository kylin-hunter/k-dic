package com.kylinhunter.nlp.dic.core.dictionary.group;

import com.kylinhunter.nlp.dic.commons.service.KServices;
import com.kylinhunter.nlp.dic.core.config.Config;
import com.kylinhunter.nlp.dic.core.config.ConfigHelper;
import com.kylinhunter.nlp.dic.core.config.DicConfig;
import com.kylinhunter.nlp.dic.core.dictionary.Dictionary;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.HitMode;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.WordNode;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
@Slf4j
public class DictionaryGroup<T> {
    private final Config config = ConfigHelper.get();
    private final Dictionary<?>[] datas = new Dictionary[GroupType.values().length];
    @Getter
    @Setter
    private boolean secondaryWordsMatch = true;
    private DicConfig dicConfig;

    public DictionaryGroup(DicConfig dicConfig) {
        this.dicConfig = dicConfig;
    }

    /**
     * @title put
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 03:04
     */
    public void put(WordNode wordNode) {
        HitMode hitMode = wordNode.getHitMode();
        if (hitMode == HitMode.HIGH) {
            get(GroupType.HIGH).put(wordNode.getKeyword(), wordNode);
            get(GroupType.HIGH_MIDDLE_LOW).put(wordNode.getKeyword(), wordNode);
            log.info("add word:" + wordNode);

        } else if (hitMode == HitMode.MIDDLE) {
            get(GroupType.MIDDLE).put(wordNode.getKeyword(), wordNode);
            get(GroupType.HIGH_MIDDLE_LOW).put(wordNode.getKeyword(), wordNode);
            get(GroupType.MIDDLE_LOW).put(wordNode.getKeyword(), wordNode);
            log.info("add word:" + wordNode);

        } else if (hitMode == HitMode.LOW) {
            get(GroupType.LOW).put(wordNode.getKeyword(), wordNode);
            get(GroupType.HIGH_MIDDLE_LOW).put(wordNode.getKeyword(), wordNode);
            get(GroupType.MIDDLE_LOW).put(wordNode.getKeyword(), wordNode);
            log.info("add word:" + wordNode);
        }

    }

    /*
     * @description  get
     * @date  2022/4/24 1:45
     * @author  BiJi'an
     * @Param groupType
     * @return com.kylinhunter.nlp.dic.core.dictionary.Dictionary<MatchWordNode>
     */
    @SuppressWarnings({"unchecked"})
    public <T> Dictionary<T> get(GroupType groupType) {
        Dictionary<T> dictionary = (Dictionary<T>) datas[groupType.ordinal()];
        if (dictionary == null) {
            synchronized(DictionaryGroup.class) {
                dictionary = (Dictionary<T>) datas[groupType.ordinal()];
                if (dictionary == null) {
                    dictionary = KServices.create(config.getDictionaryType());
                    dictionary.setSkipMaxLen(dicConfig.getSkipMaxLen());
                    datas[groupType.ordinal()] = dictionary;
                }
            }

        }
        return dictionary;
    }
}
