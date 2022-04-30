package com.kylinhunter.nlp.dic.core.loader.common;

import java.util.List;

import com.kylinhunter.nlp.dic.core.match.DicMatch;
import com.kylinhunter.nlp.dic.core.match.DicMatchCreator;
import com.kylinhunter.nlp.dic.core.match.bean.WordNode;
import com.kylinhunter.nlp.dic.core.match.component.DicSkipper;
import com.kylinhunter.nlp.dic.core.loader.DicManager;

import com.kylinhunter.nlp.dic.commons.service.KServices;
import com.kylinhunter.nlp.dic.core.analyzer.WordAnalyzer;
import com.kylinhunter.nlp.dic.core.config.Config;
import com.kylinhunter.nlp.dic.core.config.DicConfig;
import com.kylinhunter.nlp.dic.core.config.ConfigHelper;
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;
import com.kylinhunter.nlp.dic.core.dictionary.group.DictionaryGroup;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.HitMode;
import com.kylinhunter.nlp.dic.core.loader.DicLoader;
import com.kylinhunter.nlp.dic.core.loader.bean.DicData;
import com.kylinhunter.nlp.dic.core.loader.constants.DicType;
import com.kylinhunter.nlp.dic.core.loader.Dic;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01
 **/
@Slf4j
public abstract class AbstractDicLoader implements DicLoader {

    protected Config config = ConfigHelper.get();

    /**
     * @param dicType  dicType
     * @param dicDatas dicDatas
     * @return com.kylinhunter.nlp.Config.core.dictionary.group.DictionaryGroup
     * @title createDictionaryGroup
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 23:04
     */
    public DictionaryGroup<WordNode> createDictionaryGroup(DicType dicType, List<DicData> dicDatas,
                                                           DicConfig dicConfig) {
        DictionaryGroup<WordNode> dictionaryGroup = null;
        if (dicDatas != null && dicDatas.size() > 0) {
            dictionaryGroup = new DictionaryGroup<>(config.getDics().get(dicType));
            WordAnalyzer analyzer = KServices.get(config.getWordAnalyzer());
            for (DicData dicData : dicDatas) {
                addDicData(dictionaryGroup, dicData, analyzer, dicConfig.getWordMaxLen());
            }
        }

        return dictionaryGroup;
    }

    /**
     * @param dictionaryGroup dictionaryGroup
     * @param dicData         dicData
     * @title add
     * @description
     * @author BiJi'an
     * @updateTime 2022/1/26 5:48 下午
     */
    private void addDicData(DictionaryGroup<WordNode> dictionaryGroup, DicData dicData, WordAnalyzer analyzer,
                            int maxKeywordLen) {

        WordNode wordNode = DicDataHelper.convert(dicData, analyzer, maxKeywordLen);
        if (wordNode != null) {
            dictionaryGroup.put(wordNode);
            if (HitMode.HIGH == wordNode.getHitMode()) {
                DicSkipper.getInstance().remove(FindLevel.HIGH, wordNode.getKeyword());
            }
        }

    }

    /**
     * @param dicType dicType
     * @return java.util.List<com.kylinhunter.nlp.Config.core.loader.bean.DicData>
     * @title loadDicData
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 23:35
     */
    protected abstract List<DicData> loadDicData(DicType dicType, DicConfig dicConfig);

    /**
     * @param dicType dicType
     * @return com.kylinhunter.nlp.Config.core.Config.DicMatch
     * @title load
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 23:36
     */
    @Override
    public DicMatch load(DicType dicType) {
        DicConfig dicConfig = config.getDics().get(dicType);
        List<DicData> dicDatas = loadDicData(dicType, dicConfig);
        return createDicMatch(dicType, dicDatas, dicConfig);

    }

    @Override
    public void reload(DicType dicType) {
        Dic dicWrapper = DicManager.get(dicType, false);
        if (dicWrapper != null) {
            DicMatch dicMatch = this.load(dicType);
            dicWrapper.setDicMatch(dicMatch);
        }
    }

    /**
     * @param dicType  dicType
     * @param dicDatas dicDatas
     * @return com.kylinhunter.nlp.Config.core.Config.DicMatch
     * @title createDic
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 23:41
     */
    private DicMatch createDicMatch(DicType dicType, List<DicData> dicDatas, DicConfig dicConfig) {
        DictionaryGroup<WordNode> dictionaryGroup = createDictionaryGroup(dicType, dicDatas, dicConfig);
        DicMatch dicMatch = DicMatchCreator.create(dicType.getDicMatchType(), dictionaryGroup);
        log.info("createDic success,dicData'size={}", dicDatas.size());
        return dicMatch;
    }
}
