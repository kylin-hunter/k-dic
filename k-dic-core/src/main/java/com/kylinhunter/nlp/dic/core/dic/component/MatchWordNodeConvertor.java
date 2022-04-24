package com.kylinhunter.nlp.dic.core.dic.component;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.kylinhunter.nlp.dic.core.analyzer.WordAnalyzer;
import com.kylinhunter.nlp.dic.core.analyzer.bean.Words;
import com.kylinhunter.nlp.dic.core.dic.bean.MatchWordNode;
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
    public static MatchWordNode convert(HitMode hitMode, String words, String secondaryWords, String relationWords,
                                        WordAnalyzer analyzer,
                                        int maxKeywordLen) {
        if (!StringUtils.isEmpty(words)) {
            MatchWordNode matchWordNode = new MatchWordNode();
            matchWordNode.setHitMode(hitMode);

            if (!StringUtils.isEmpty(words) && words.length() > 0 && words.length() <= maxKeywordLen) {
                matchWordNode.setKeyword(words);
                matchWordNode.setKeywordSplit(analyzer.analyze(words));

                if (!StringUtils.isEmpty(secondaryWords)) {

                    List<String> secondaryWordsList = new ArrayList<>();
                    List<Words> secondaryWordsSplitList = new ArrayList<>();

                    for (String secondaryWord : StringUtils.split(secondaryWords, ',')) {
                        if (!StringUtils.isEmpty(secondaryWord) && secondaryWord.length() > 1
                                && secondaryWord.length() <= maxKeywordLen) {
                            secondaryWordsList.add(secondaryWord);
                            secondaryWordsSplitList.add(analyzer.analyze(secondaryWord));
                        }

                    }
                    if (secondaryWordsList.size() > 0) {
                        matchWordNode.setSecondaryWords(secondaryWordsList.toArray(new String[0]));
                        matchWordNode.setSecondaryWordsSplit(secondaryWordsSplitList.toArray(new Words[0]));
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
