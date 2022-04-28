package com.kylinhunter.nlp.dic.core.loader.common;

import com.kylinhunter.nlp.dic.core.analyzer.WordAnalyzer;
import com.kylinhunter.nlp.dic.core.match.bean.WordNode;
import com.kylinhunter.nlp.dic.core.match.component.WordNodeConvertor;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.HitMode;
import com.kylinhunter.nlp.dic.core.loader.bean.DicData;

/**
 * @description
 * @author BiJi'an
 * @create 2022-04-25 01:19
 **/
public class DicDataHelper {
    public static WordNode convert(DicData dicData, WordAnalyzer analyzer, int maxKeywordLen) {
        HitMode hitMode = HitMode.valueOf(dicData.getHitMode().toUpperCase());
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
}
