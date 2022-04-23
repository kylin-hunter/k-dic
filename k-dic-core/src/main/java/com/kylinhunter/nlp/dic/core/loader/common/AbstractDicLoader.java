package com.kylinhunter.nlp.dic.core.loader.common;

import java.util.ArrayList;
import java.util.List;

import com.kylinhunter.nlp.dic.core.dic.component.DicSkipper;
import com.kylinhunter.nlp.dic.core.loader.DicManager;
import com.kylinhunter.nlp.dic.core.loader.wrapper.DicWrapper;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import com.kylinhunter.nlp.dic.commons.service.KServices;
import com.kylinhunter.nlp.dic.core.analyzer.WordAnalyzer;
import com.kylinhunter.nlp.dic.core.analyzer.bean.Words;
import com.kylinhunter.nlp.dic.core.config.Config;
import com.kylinhunter.nlp.dic.core.config.DicConfig;
import com.kylinhunter.nlp.dic.core.config.ConfigHelper;
import com.kylinhunter.nlp.dic.core.dic.Dic;
import com.kylinhunter.nlp.dic.core.dic.imp.DicImp;
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;
import com.kylinhunter.nlp.dic.core.dictionary.group.DictionaryGroup;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.HitMode;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.WordNode;
import com.kylinhunter.nlp.dic.core.loader.DicLoader;
import com.kylinhunter.nlp.dic.core.loader.bean.DicData;
import com.kylinhunter.nlp.dic.core.loader.constants.DicType;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01
 **/
@Slf4j
public abstract class AbstractDicLoader implements DicLoader {

    protected DicSkipper dicSkipper = DicSkipper.getInstance();
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
    public DictionaryGroup createDictionaryGroup(DicType dicType, List<DicData> dicDatas,
                                                 DicConfig dicConfig) {
        DictionaryGroup dictionaryGroup = null;
        if (dicDatas != null && dicDatas.size() > 0) {
            dictionaryGroup = new DictionaryGroup();
            WordAnalyzer analyzer = KServices.get(config.getWordAnalyzer());
            for (DicData dicData : dicDatas) {
                addDicData(dictionaryGroup, dicData, analyzer, dicConfig.getWordMaxLen());
            }
            dictionaryGroup.setSecondaryWordsMatch(dicType.isSecondaryWordsMatch());
        }

        return dictionaryGroup;
    }

    /**
     * @param dictionaryGroup dictionaryGroup
     * @param dicData         dicData
     * @title add
     * @description
     * @author BiJi'an
     * @updateTime 2022/3/26 5:48 下午
     */
    private void addDicData(DictionaryGroup dictionaryGroup, DicData dicData, WordAnalyzer analyzer, int maxKeywordLen) {
        String words = dicData.getWords();
        if (!StringUtils.isEmpty(words)) {
            WordNode wordNode = new WordNode();
            wordNode.setType(dicData.getType());
            wordNode.setClassId(dicData.getClassId());
            wordNode.setHitMode(HitMode.valueOf(dicData.getHitMode().toUpperCase()));


            String relationWordsOri = dicData.getRelationWords();
            String[] relationWordsOriSplit = StringUtils.split(relationWordsOri, ' ');
            wordNode.setRelationWords(relationWordsOriSplit);


            if (!StringUtils.isEmpty(words) && words.length() > 0 && words.length() <= maxKeywordLen) {
                wordNode.setKeyword(words);
                wordNode.setKeywordSplit(analyzer.analyze(words));

                String secondaryWords = dicData.getSecondaryWords();
                if (!StringUtils.isEmpty(secondaryWords)) {

                    List<String> secondaryWordsList = new ArrayList<>();
                    List<Words> secondaryWordsSplitList = new ArrayList<>();

                    for (String secondaryWord : StringUtils.split(secondaryWords, ' ')) {
                        if (!StringUtils.isEmpty(secondaryWord) && secondaryWord.length() > 1
                                && secondaryWord.length() <= maxKeywordLen) {
                            secondaryWordsList.add(secondaryWord);
                            secondaryWordsSplitList.add(analyzer.analyze(secondaryWord));
                        }

                    }
                    if (secondaryWordsList.size() > 0) {
                        wordNode.setSecondaryWords(secondaryWordsList.toArray(new String[0]));
                        wordNode.setSecondaryWordsSplit(secondaryWordsSplitList.toArray(new Words[0]));
                    }
                }

                dictionaryGroup.put(wordNode);

                if (HitMode.HIGH == wordNode.getHitMode()) {
                    for (int i = 0; i < words.length(); i++) {
                        if (words.charAt(i) != ' ' && !CharUtils.isAsciiAlphanumeric(words.charAt(i))) {
                            if (dicSkipper.remove(FindLevel.HIGH, words.charAt(i))) {
                                log.error("remove FindLevel.HIGH char skip:" + words.charAt(i));
                            }
                        }
                    }
                }
            }

        } else {
            if (!StringUtils.isEmpty(words)) {
                log.warn("invalid word:" + dicData);
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
     * @return com.kylinhunter.nlp.Config.core.Config.Dic
     * @title load
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 23:36
     */
    @Override
    public Dic load(DicType dicType) {
        DicConfig dicConfig = config.getDics().get(dicType);
        List<DicData> dicDatas = loadDicData(dicType, dicConfig);
        return createDic(dicType, dicDatas, dicConfig);

    }

    @Override
    public void reload(DicType dicType) {
        DicWrapper dicWrapper = DicManager.getDicWrapper(dicType);
        if (dicWrapper != null) {
            Dic dic = this.load(dicType);
            dicWrapper.setDic(dic);
        }
    }

    /**
     * @param dicType  dicType
     * @param dicDatas dicDatas
     * @return com.kylinhunter.nlp.Config.core.Config.Dic
     * @title createDic
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 23:41
     */
    private Dic createDic(DicType dicType, List<DicData> dicDatas, DicConfig dicConfig) {
        WordAnalyzer analyzer = KServices.get(config.getWordAnalyzer());
        DictionaryGroup dictionaryGroup = createDictionaryGroup(dicType, dicDatas, dicConfig);
        Dic dic = new DicImp(dictionaryGroup, analyzer);
        log.info("createDic success,dicData'size={}", dicDatas.size());
        return dic;
    }
}
