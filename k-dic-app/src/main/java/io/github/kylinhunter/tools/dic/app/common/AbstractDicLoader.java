package io.github.kylinhunter.tools.dic.app.common;

import java.util.List;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.tools.dic.app.DicAPP;
import io.github.kylinhunter.tools.dic.app.DicLoader;
import io.github.kylinhunter.tools.dic.app.DicManager;
import io.github.kylinhunter.tools.dic.app.bean.DicData;
import io.github.kylinhunter.tools.dic.app.config.Config;
import io.github.kylinhunter.tools.dic.app.config.ConfigHelper;
import io.github.kylinhunter.tools.dic.app.config.DicConfig;
import io.github.kylinhunter.tools.dic.app.constants.DicType;
import io.github.kylinhunter.tools.dic.core.dictionary.DictionaryGroup;
import io.github.kylinhunter.tools.dic.core.dictionary.bean.WordNode;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.HitMode;
import io.github.kylinhunter.tools.dic.core.dictionary.helper.DictionarySkipper;
import io.github.kylinhunter.tools.dic.core.match.DictionaryMatcher;
import io.github.kylinhunter.tools.dic.words.analyzer.WordAnalyzer;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01
 **/
@Slf4j
public abstract class AbstractDicLoader implements DicLoader {

    protected Config config = ConfigHelper.get();
    protected DictionarySkipper dictionarySkipper = CF.get(DictionarySkipper.class);

    /**
     * @param dicType  dicType
     * @param dicDatas dicDatas
     * @return io.github.kylinhunter.toolsConfig.core.dictionary.group.DictionaryGroup
     * @title createDictionaryGroup
     * @description
     * @author BiJi'an
     * @date 2022-01-01 23:04
     */
    public DictionaryGroup createDictionaryGroup(DicType dicType, List<DicData> dicDatas,
                                                 DicConfig dicConfig) {

        int unclearSkipMaxLen = dicConfig.getUnclearSkipMaxLen();

        DictionaryGroup dictionaryGroup = new DictionaryGroup(unclearSkipMaxLen);
        if (dicDatas != null && dicDatas.size() > 0) {
            WordAnalyzer analyzer = CF.get(config.getWordAnalyzer().clazz);
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
     * @date 2022/1/26 5:48 下午
     */
    private void addDicData(DictionaryGroup dictionaryGroup, DicData dicData, WordAnalyzer analyzer,
                            int maxKeywordLen) {

        WordNode wordNode = DicDataHelper.convert(dicData, analyzer, maxKeywordLen);
        if (wordNode != null) {
            dictionaryGroup.put(wordNode);
            if (HitMode.HIGH == wordNode.getHitMode()) {
                dictionarySkipper.remove(FindLevel.HIGH, wordNode.getKeyword());
            }
        }

    }

    /**
     * @param dicType dicType
     * @return java.util.List<io.github.kylinhunter.toolsConfig.core.loader.bean.DicData>
     * @title loadDicData
     * @description
     * @author BiJi'an
     * @date 2022-01-01 23:35
     */
    protected abstract List<DicData> loadDicData(DicType dicType, DicConfig dicConfig);

    /**
     * @param dicType dicType
     * @return io.github.kylinhunter.toolsConfig.core.Config.DictionaryMatcher
     * @title load
     * @description
     * @author BiJi'an
     * @date 2022-01-01 23:36
     */
    @Override
    public DictionaryMatcher load(DicType dicType) {
        DicConfig dicConfig = config.getDics().get(dicType);
        List<DicData> dicDatas = loadDicData(dicType, dicConfig);
        return createDicMatch(dicType, dicDatas, dicConfig);

    }

    @Override
    public void reload(DicType dicType) {
        DicAPP dicAPPWrapper = DicManager.get(dicType, false);
        if (dicAPPWrapper != null) {
            DictionaryMatcher dictionaryMatcher = this.load(dicType);
            dicAPPWrapper.setDictionaryMatcher(dictionaryMatcher);
        }
    }

    /**
     * @param dicType  dicType
     * @param dicDatas dicDatas
     * @return io.github.kylinhunter.toolsConfig.core.Config.DictionaryMatcher
     * @title createDic
     * @description
     * @author BiJi'an
     * @date 2022-01-01 23:41
     */
    private DictionaryMatcher createDicMatch(DicType dicType, List<DicData> dicDatas, DicConfig dicConfig) {

        DictionaryMatcher dictionaryMatcher = CF.get(dicConfig.getMatcherType());
        dictionaryMatcher.setWordAnalyzer(CF.get(config.getWordAnalyzer()));
        dictionaryMatcher.setDictionaryGroup(createDictionaryGroup(dicType, dicDatas, dicConfig));

        log.info("createDic success,dicData'size={}", dicDatas.size());
        return dictionaryMatcher;
    }
}
