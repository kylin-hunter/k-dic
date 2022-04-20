package com.kylinhunter.nlp.dic.core.loader.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import com.kylinhunter.nlp.dic.commons.service.SimpleServiceFactory;
import com.kylinhunter.nlp.dic.core.analyzer.WordAnalyzer;
import com.kylinhunter.nlp.dic.core.analyzer.bean.Words;
import com.kylinhunter.nlp.dic.core.config.Config;
import com.kylinhunter.nlp.dic.core.config.DicConfig;
import com.kylinhunter.nlp.dic.core.config.DicConfigParser;
import com.kylinhunter.nlp.dic.core.dic.Dic;
import com.kylinhunter.nlp.dic.core.dic.imp.DicImp;
import com.kylinhunter.nlp.dic.core.dictionary.component.FindSkipper;
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;
import com.kylinhunter.nlp.dic.core.dictionary.group.DictionaryGroup;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.HitMode;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.WordNode;
import com.kylinhunter.nlp.dic.core.loader.DicLoader;
import com.kylinhunter.nlp.dic.core.loader.bean.DicData;
import com.kylinhunter.nlp.dic.core.loader.constants.DicType;

import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2022-01-01
 **/
@Slf4j
public abstract class AbstractDicLoader implements DicLoader {

    protected FindSkipper findSkipper = FindSkipper.getInstance();
    protected Dic[] dics = new Dic[DicType.values().length];
    protected Config config = DicConfigParser.load();

    public AbstractDicLoader() {

    }

    /**
     * @param dicType
     * @param dicDatas
     * @return com.kylinhunter.nlp.Config.core.dictionary.group.DictionaryGroup
     * @throws
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
            WordAnalyzer analyzer = SimpleServiceFactory.get(config.getWordAnalyzer());
            for (DicData dicData : dicDatas) {
                add(dictionaryGroup, dicData, analyzer, dicConfig.getWordMaxLen());
            }
            dictionaryGroup.setSecondaryWordsMatch(dicType.isSecondaryWordsMatch());
        }

        return dictionaryGroup;
    }

    /**
     * @param dictionaryGroup
     * @param dicData
     * @return void
     * @throws
     * @title add
     * @description
     * @author BiJi'an
     * @updateTime 2022/3/26 5:48 下午
     */
    private void add(DictionaryGroup dictionaryGroup, DicData dicData, WordAnalyzer analyzer, int maxKeywordLen) {
        String wordsOri = dicData.getWords();
        String secondaryWordsOri = dicData.getSecondaryWords();
        int classId = dicData.getClassId();
        int type = dicData.getType();
        if (!StringUtils.isEmpty(wordsOri)) {
            WordNode wordNode = new WordNode();
            String relationWordsOri = dicData.getRelationWords();
            String[] relationWordsOriSplit = StringUtils.split(relationWordsOri, ' ');
            wordNode.setRelationWords(relationWordsOriSplit);
            wordNode.setType(type);
            wordNode.setClassId(classId);

            wordNode.setHitMode(HitMode.valueOf(dicData.getHitMode().toUpperCase()));
            if (!StringUtils.isEmpty(wordsOri) && wordsOri.length() > 1 && wordsOri.length() <= maxKeywordLen) {
                wordNode.setKeyword(wordsOri);
                wordNode.setKeywordSplit(analyzer.analyze(wordsOri));

                if (!StringUtils.isEmpty(secondaryWordsOri)) {
                    String[] secondaryWordsOriSplit = StringUtils.split(secondaryWordsOri, ' ');
                    List<String> secondKeyWords = new ArrayList<>();
                    List<Words> secondaryWordsSplit = new ArrayList<>();

                    for (String nowWord : secondaryWordsOriSplit) {
                        if (!StringUtils.isEmpty(nowWord) && nowWord.length() > 1
                                && nowWord.length() <= maxKeywordLen) {
                            secondKeyWords.add(nowWord);
                            secondaryWordsSplit.add(analyzer.analyze(nowWord));
                        }

                    }
                    if (secondKeyWords.size() > 0) {
                        wordNode.setSecondaryWords(secondKeyWords.toArray(new String[0]));
                        wordNode.setSecondaryWordsSplit(secondaryWordsSplit.toArray(new Words[0]));
                    }
                }

                dictionaryGroup.put(wordNode);

                if (HitMode.HIGH == wordNode.getHitMode()) {
                    for (int i = 0; i < wordsOri.length(); i++) {
                        if (wordsOri.charAt(i) != ' ' && !CharUtils.isAsciiAlphanumeric(wordsOri.charAt(i))) {
                            if (findSkipper.remove(FindLevel.HIGH, wordsOri.charAt(i))) {
                                log.error("remove FindLevel.HIGH char skip:" + wordsOri.charAt(i));
                            }
                        }
                    }
                }
            }

        } else {
            log.warn("invalid word:" + dicData);
        }
    }

    /**
     * @param dicType
     * @return java.util.List<com.kylinhunter.nlp.Config.core.loader.bean.DicData>
     * @throws
     * @title loadDicData
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 23:35
     */
    protected abstract List<DicData> loadDicData(DicType dicType, DicConfig dicConfig);

    /**
     * @param dicType
     * @return com.kylinhunter.nlp.Config.core.Config.Dic
     * @throws
     * @title load
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 23:36
     */
    @Override
    public Dic load(DicType dicType) {
        return load(dicType, false);
    }

    @Override

    public Dic load(DicType dicType, boolean force) {
        Dic dic = dics[dicType.ordinal()];
        if (dic == null || force) {
            DicConfig dicConfig = config.getDics().get(dicType);
            List<DicData> dicDatas = loadDicData(dicType, dicConfig);
            dics[dicType.ordinal()] = createDic(dicType, dicDatas, dicConfig);
            dic = dics[dicType.ordinal()];
        }
        return dic;
    }

    /**
     * @param dicType
     * @param dicDatas
     * @return com.kylinhunter.nlp.Config.core.Config.Dic
     * @throws
     * @title createDic
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 23:41
     */
    private Dic createDic(DicType dicType, List<DicData> dicDatas, DicConfig dicConfig) {
        WordAnalyzer analyzer = SimpleServiceFactory.get(config.getWordAnalyzer());
        DictionaryGroup dictionaryGroup = createDictionaryGroup(dicType, dicDatas, dicConfig);
        Dic dic = new DicImp(dictionaryGroup, analyzer);
        log.info("createDic success,dicData'size={}", dicDatas.size());
        return dic;
    }
}
