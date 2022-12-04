package io.github.kylinhunter.tools.dic.core.match.imp;

import java.util.Collections;
import java.util.List;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.util.CollectionUtils;
import io.github.kylinhunter.tools.dic.core.dictionary.Dictionary;
import io.github.kylinhunter.tools.dic.core.dictionary.DictionaryGroup;
import io.github.kylinhunter.tools.dic.core.dictionary.bean.WordNode;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.helper.DictionarySkipper;
import io.github.kylinhunter.tools.dic.core.match.DictionaryMatcher;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;
import io.github.kylinhunter.tools.dic.words.analyzer.WordAnalyzer;
import io.github.kylinhunter.tools.dic.words.analyzer.WordAnalyzerType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractDictionaryMatcher implements DictionaryMatcher {
    protected DictionarySkipper dictionarySkipper = CF.get(DictionarySkipper.class);
    protected DictionaryGroup dictionaryGroup;
    protected WordAnalyzer analyzer;
    protected boolean assistMatchEnabled;

    public AbstractDictionaryMatcher(DictionaryGroup dictionaryGroup, WordAnalyzerType wordAnalyzerType) {
        this(dictionaryGroup, wordAnalyzerType, true);
    }

    public AbstractDictionaryMatcher(DictionaryGroup dictionaryGroup, WordAnalyzerType wordAnalyzerType,
                                     boolean assistMatchEnabled) {
        this.dictionaryGroup = dictionaryGroup;
        this.analyzer = CF.get(wordAnalyzerType);
        this.assistMatchEnabled = assistMatchEnabled;

    }

    @Override
    public List<MatchResult> match(String text, FindLevel findLevel) {
        List<MatchResult> result = null;
        switch (findLevel) {
            case HIGH: {
                result = process(text, FindLevel.HIGH, dictionaryGroup.getHighMiddleLow());
                break;
            }
            case HIGH_MIDDLE: {

                List<MatchResult> resultHigh = process(text, FindLevel.HIGH, dictionaryGroup.getHigh());
                List<MatchResult> resultMiddle = process(text, FindLevel.HIGH_MIDDLE, dictionaryGroup.getMiddleLow());
                result = CollectionUtils.merge(true, resultHigh, resultMiddle);
                break;
            }
            case HIGH_MIDDLE_LOW: {

                List<MatchResult> resultHigh = process(text, FindLevel.HIGH, dictionaryGroup.getHigh());
                List<MatchResult> resultMiddle = process(text, FindLevel.HIGH_MIDDLE, dictionaryGroup.getMiddle());
                List<MatchResult> resultLow = process(text, FindLevel.HIGH_MIDDLE_LOW, dictionaryGroup.getLow());
                result = CollectionUtils.merge(true, resultHigh, resultMiddle, resultLow);
                break;
            }
        }
        return result != null ? result : Collections.EMPTY_LIST;

    }

    protected abstract List<MatchResult> process(String text, FindLevel high, Dictionary<WordNode> dictionary);
}
