package com.kylinhunter.nlp.dic.core.dic.common;

import org.apache.commons.lang3.StringUtils;

import com.kylinhunter.nlp.dic.core.words.analyzer.WordAnalyzer;
import com.kylinhunter.nlp.dic.core.dic.bean.DicData;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.HitMode;
import com.kylinhunter.nlp.dic.core.match.bean.WordNode;
import com.kylinhunter.nlp.dic.core.match.component.WordNodeConvertor;

/**
 * @author BiJi'an
 * @description
 * @create 2022-01-25 01:19
 **/
public class DicDataHelper {
    public static WordNode convert(DicData dicData, WordAnalyzer analyzer, int maxKeywordLen) {
        if (!StringUtils.isEmpty(dicData.getWords()) && dicData.getHitMode() != null) {
            HitMode hitMode = dicData.getHitMode();
            String words = dicData.getWords();
            String assistWords = dicData.getAssistWords();
            String relationWords = dicData.getRelationWords();
            WordNode wordNode = WordNodeConvertor.convert(hitMode, words, assistWords, relationWords,
                    analyzer, maxKeywordLen);
            if (wordNode != null) {
                wordNode.setType(dicData.getType());
                wordNode.setClassId(dicData.getClassId());
            }
            return wordNode;
        }
        return null;

    }
}
