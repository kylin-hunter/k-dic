package io.github.kylinhunter.tools.dic.core.dictionary.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.tools.dic.core.dictionary.bean.WordNode;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.HitMode;
import io.github.kylinhunter.tools.dic.core.trie.TireConst;
import io.github.kylinhunter.tools.dic.words.analyzer.WordAnalyzer;
import io.github.kylinhunter.tools.dic.words.analyzer.WordAnalyzerType;
import io.github.kylinhunter.tools.dic.words.analyzer.bean.Words;

/**
 * @author BiJi'an
 * @description WordNodeConvertor
 * @date 2022/12/4
 **/
public class WordNodeConvertor {
    private static WordAnalyzer wordAnalyzer = CF.get(WordAnalyzerType.DEFAULT);

    public static WordNode convert(HitMode hitMode, String keyword, String assistedKeywords, String targetWords) {

        return convert(hitMode, keyword, assistedKeywords, targetWords, wordAnalyzer,
                TireConst.DEFAULT_MAX_WORD_LENGTH);
    }

    /**
     * @param analyzer      analyzer
     * @param maxKeywordLen maxKeywordLen
     * @return io.github.kylinhunter.toolsdic.core.dictionary.group.bean.WordNode
     * @title convert
     * @description
     * @author BiJi'an
     * @date 2022-01-24 23:55
     */
    public static WordNode convert(HitMode hitMode, String keyword, String assistedKeywords, String targetWords,
                                   WordAnalyzer analyzer,
                                   int maxKeywordLen) {
        if (!StringUtils.isEmpty(keyword)) {
            keyword = keyword.trim();

            WordNode wordNode = new WordNode();
            wordNode.setHitMode(hitMode);

            if (keyword.length() > 0 && keyword.length() <= maxKeywordLen) {
                wordNode.setKeyword(keyword);
                wordNode.setAnalyzedKeywords(analyzer.analyze(keyword));

                List<String> assistWordsList = new ArrayList<>();
                List<Words> assistWordsSplitList = new ArrayList<>();
                if (!StringUtils.isEmpty(assistedKeywords)) {
                    assistedKeywords = assistedKeywords.trim();

                    for (String assistedKeyword : StringUtils.split(assistedKeywords, ',')) {
                        if (!StringUtils.isEmpty(assistedKeyword) && assistedKeyword.length() <= maxKeywordLen) {
                            assistWordsList.add(assistedKeyword);
                            assistWordsSplitList.add(analyzer.analyze(assistedKeyword));
                        }
                    }
                }
                wordNode.setAssistedKeywords(assistWordsList.toArray(new String[0]));
                wordNode.setAnalyzedRelatedKeywords(assistWordsSplitList.toArray(new Words[0]));

                if (!StringUtils.isEmpty(targetWords)) {
                    wordNode.setTargetWords(StringUtils.split(targetWords, ','));
                }
                return wordNode;
            }

        }
        return null;
    }
}
