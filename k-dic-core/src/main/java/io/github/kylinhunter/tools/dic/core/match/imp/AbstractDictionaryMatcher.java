package io.github.kylinhunter.tools.dic.core.match.imp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.util.CollectionUtils;
import io.github.kylinhunter.tools.dic.core.dictionary.Dictionary;
import io.github.kylinhunter.tools.dic.core.dictionary.DictionaryGroup;
import io.github.kylinhunter.tools.dic.core.dictionary.WordNodeAware;
import io.github.kylinhunter.tools.dic.core.dictionary.bean.WordNode;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.helper.DictionarySkipper;
import io.github.kylinhunter.tools.dic.core.match.DictionaryMatcher;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;
import io.github.kylinhunter.tools.dic.words.analyzer.WordAnalyzer;
import io.github.kylinhunter.tools.dic.words.analyzer.WordAnalyzerType;
import io.github.kylinhunter.tools.dic.words.analyzer.bean.Words;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class AbstractDictionaryMatcher<T extends WordNode, R extends WordNodeAware>
        implements DictionaryMatcher<T, R> {
    protected DictionarySkipper dictionarySkipper = CF.get(DictionarySkipper.class);
    protected DictionaryGroup<T> dictionaryGroup = new DictionaryGroup<>();
    protected WordAnalyzer wordAnalyzer = CF.get(WordAnalyzerType.DEFAULT);
    protected boolean assistMatchEnabled = true;

    @Override
    public List<MatchResult<R>> match(String text, FindLevel findLevel) {
        List<MatchResult<R>> result = null;
        switch (findLevel) {
            case HIGH: {
                result = process(text, FindLevel.HIGH, dictionaryGroup.getHighMiddleLow());
                break;
            }
            case HIGH_MIDDLE: {

                List<MatchResult<R>> resultHigh = process(text, FindLevel.HIGH, dictionaryGroup.getHigh());
                List<MatchResult<R>> resultMiddle =
                        process(text, FindLevel.HIGH_MIDDLE, dictionaryGroup.getMiddleLow());
                result = CollectionUtils.merge(true, resultHigh, resultMiddle);
                break;
            }
            case HIGH_MIDDLE_LOW: {

                List<MatchResult<R>> resultHigh = process(text, FindLevel.HIGH, dictionaryGroup.getHigh());
                List<MatchResult<R>> resultMiddle = process(text, FindLevel.HIGH_MIDDLE, dictionaryGroup.getMiddle());
                List<MatchResult<R>> resultLow = process(text, FindLevel.HIGH_MIDDLE_LOW, dictionaryGroup.getLow());
                result = CollectionUtils.merge(true, resultHigh, resultMiddle, resultLow);
                break;
            }
        }
        return result != null ? result : Collections.emptyList();

    }

    /**
     * @param wordNode wordNode
     * @return void
     * @title addWord
     * @description
     * @author BiJi'an
     * @date 2022-12-04 19:57
     */
    public void addWord(T wordNode) {
        this.analyzer(wordNode);
        this.dictionaryGroup.put(wordNode);
    }

    /**
     * @param wordNode wordNode
     * @return void
     * @title analyzer
     * @description
     * @author BiJi'an
     * @date 2022-12-05 00:25
     */
    public void analyzer(WordNode wordNode) {
        String keyword = wordNode.getWord();
        if (!StringUtils.isEmpty(keyword)) {
            wordNode.setAnalyzedWords(wordAnalyzer.analyze(keyword));
        }
        String[] assistedKeywords = wordNode.getAssistedWords();
        if (assistedKeywords != null && assistedKeywords.length > 0) {
            List<Words> assistedWords = new ArrayList<>();
            for (String assistedKeyword : assistedKeywords) {
                assistedWords.add(wordAnalyzer.analyze(assistedKeyword));
            }
            wordNode.setAnalyzedAssistedWords(assistedWords.toArray(new Words[0]));
        }

    }

    protected abstract List<MatchResult<R>> process(String text, FindLevel high, Dictionary<T> dictionary);
}
