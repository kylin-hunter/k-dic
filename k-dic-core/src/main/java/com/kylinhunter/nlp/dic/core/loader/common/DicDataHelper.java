package com.kylinhunter.nlp.dic.core.loader.common;

import com.kylinhunter.nlp.dic.core.analyzer.WordAnalyzer;
import com.kylinhunter.nlp.dic.core.match.bean.MatchWordNode;
import com.kylinhunter.nlp.dic.core.match.component.MatchWordNodeConvertor;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.HitMode;
import com.kylinhunter.nlp.dic.core.loader.bean.DicData;

/**
 * @description
 * @author BiJi'an
 * @create 2022-04-25 01:19
 **/
public class DicDataHelper {
    public static MatchWordNode convert(DicData dicData, WordAnalyzer analyzer, int maxKeywordLen) {
        HitMode hitMode = HitMode.valueOf(dicData.getHitMode().toUpperCase());
        String words = dicData.getWords();
        String assistWords = dicData.getAssistWords();
        String relationWords = dicData.getRelationWords();
        MatchWordNode matchWordNode = MatchWordNodeConvertor.convert(hitMode, words, assistWords, relationWords,
                analyzer, maxKeywordLen);
        if (matchWordNode != null) {
            matchWordNode.setType(dicData.getType());
            matchWordNode.setClassId(dicData.getClassId());
        }
        return matchWordNode;

    }
}
