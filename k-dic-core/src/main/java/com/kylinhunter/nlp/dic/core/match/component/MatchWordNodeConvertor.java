package com.kylinhunter.nlp.dic.core.match.component;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.kylinhunter.nlp.dic.core.analyzer.WordAnalyzer;
import com.kylinhunter.nlp.dic.core.analyzer.bean.Words;
import com.kylinhunter.nlp.dic.core.match.bean.MatchWordNode;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.HitMode;

/**
 * @author BiJi'an
 * @description
 * @create 2022-04-24 23:49
 **/
public class MatchWordNodeConvertor {

    /**
     * @param analyzer      analyzer
     * @param maxKeywordLen maxKeywordLen
     * @return com.kylinhunter.nlp.dic.core.dictionary.group.bean.MatchWordNode
     * @title convert
     * @description
     * @author BiJi'an
     * @updateTime 2022-04-24 23:55
     */
    public static MatchWordNode convert(HitMode hitMode, String words, String assistWords, String relationWords,
                                        WordAnalyzer analyzer,
                                        int maxKeywordLen) {
        if (!StringUtils.isEmpty(words)) {
            MatchWordNode matchWordNode = new MatchWordNode();
            matchWordNode.setHitMode(hitMode);
            words = words.trim();

            if (words.length() > 0 && words.length() <= maxKeywordLen) {
                matchWordNode.setKeyword(words);
                matchWordNode.setKeywordSplit(analyzer.analyze(words));

                if (!StringUtils.isEmpty(assistWords)) {
                    assistWords = assistWords.trim();

                    List<String> assistWordsList = new ArrayList<>();
                    List<Words> assistWordsSplitList = new ArrayList<>();

                    for (String assistWord : StringUtils.split(assistWords, ',')) {
                        if (!StringUtils.isEmpty(assistWord) && assistWord.length() > 0
                                && assistWord.length() <= maxKeywordLen) {
                            assistWordsList.add(assistWord);
                            assistWordsSplitList.add(analyzer.analyze(assistWord));
                        }

                    }
                    if (assistWordsList.size() > 0) {
                        matchWordNode.setAssistWords(assistWordsList.toArray(new String[0]));
                        matchWordNode.setAssistWordsSplit(assistWordsSplitList.toArray(new Words[0]));
                    }
                }

                String[] relationWordsSplit = StringUtils.split(relationWords, ',');
                matchWordNode.setRelationWords(relationWordsSplit);

                return matchWordNode;
            }

        }
        return null;
    }
}
