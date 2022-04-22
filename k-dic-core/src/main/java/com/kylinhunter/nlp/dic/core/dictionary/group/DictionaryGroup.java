package com.kylinhunter.nlp.dic.core.dictionary.group;

import com.kylinhunter.nlp.dic.commons.service.SimpleServiceFactory;
import com.kylinhunter.nlp.dic.core.dictionary.DictionaryType;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.WordNode;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.HitMode;
import com.kylinhunter.nlp.dic.core.dictionary.Dictionary;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022/1/1
 **/
@Slf4j
public class DictionaryGroup {
    private Dictionary<WordNode>[] datas = new Dictionary[DictionaryGroupType.values().length];
    @Getter
    @Setter
    private boolean secondaryWordsMatch = true;

    /**
     * @param wordNode
     * @return void
     * @throws
     * @title put
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 03:04
     */
    public void put(WordNode wordNode) {
        HitMode hitMode = wordNode.getHitMode();
        if (hitMode == HitMode.HIGH) {
            getDic(DictionaryGroupType.HIGH).put(wordNode.getKeyword(), wordNode);
            getDic(DictionaryGroupType.HIGH_MIDDLE_LOW).put(wordNode.getKeyword(), wordNode);
            log.info("add word:" + wordNode);

        } else if (hitMode == HitMode.MIDDLE) {
            getDic(DictionaryGroupType.MIDDLE).put(wordNode.getKeyword(), wordNode);
            getDic(DictionaryGroupType.HIGH_MIDDLE_LOW).put(wordNode.getKeyword(), wordNode);
            getDic(DictionaryGroupType.MIDDLE_LOW).put(wordNode.getKeyword(), wordNode);
            log.info("add word:" + wordNode);

        } else if (hitMode == HitMode.LOW) {
            getDic(DictionaryGroupType.LOW).put(wordNode.getKeyword(), wordNode);
            getDic(DictionaryGroupType.HIGH_MIDDLE_LOW).put(wordNode.getKeyword(), wordNode);
            getDic(DictionaryGroupType.MIDDLE_LOW).put(wordNode.getKeyword(), wordNode);
            log.info("add word:" + wordNode);
        }

    }

    /**
     * @param hitMode
     * @return com.kylinhunter.nlp.Config.core.Config.single.Dictionary<com.kylinhunter.nlp.Config.core.Config.group.bean.WordNode>
     * @throws
     * @title getDic
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 03:04
     */
    public Dictionary<WordNode> getDic(DictionaryGroupType hitMode) {
        Dictionary<WordNode> dictionary = datas[hitMode.ordinal()];
        if (dictionary == null) {
            dictionary = SimpleServiceFactory.create(DictionaryType.TRIE);
            datas[hitMode.ordinal()] = dictionary;
        }
        return dictionary;
    }
}
