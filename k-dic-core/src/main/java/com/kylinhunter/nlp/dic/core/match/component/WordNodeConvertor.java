package com.kylinhunter.nlp.dic.core.match.component;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.kylinhunter.nlp.dic.core.dictionary.group.bean.HitMode;
import com.kylinhunter.nlp.dic.core.match.bean.WordNode;
import io.github.kylinhunter.dic.words.analyzer.WordAnalyzer;
import io.github.kylinhunter.dic.words.analyzer.bean.Words;

/**
 * @author BiJi'an
 * @description
 * @create 2022-01-24 23:49
 **/
public class WordNodeConvertor {

    /**
     * @param analyzer      analyzer
     * @param maxKeywordLen maxKeywordLen
     * @return com.kylinhunter.nlp.dic.core.dictionary.group.bean.WordNode
     * @title convert
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-24 23:55
     */
    public static WordNode convert(HitMode hitMode, String words, String assistWords, String relationWords,
                                   WordAnalyzer analyzer,
                                   int maxKeywordLen) {
        if (!StringUtils.isEmpty(words)) {
            WordNode wordNode = new WordNode();
            wordNode.setHitMode(hitMode);
            words = words.trim();

            if (words.length() > 0 && words.length() <= maxKeywordLen) {
                wordNode.setKeyword(words);
                wordNode.setKeywordSplit(analyzer.analyze(words));

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
                        wordNode.setAssistWords(assistWordsList.toArray(new String[0]));
                        wordNode.setAssistWordsSplit(assistWordsSplitList.toArray(new Words[0]));
                    }
                }

                String[] relationWordsSplit = StringUtils.split(relationWords, ',');
                wordNode.setRelationWords(relationWordsSplit);

                return wordNode;
            }

        }
        return null;
    }
}
