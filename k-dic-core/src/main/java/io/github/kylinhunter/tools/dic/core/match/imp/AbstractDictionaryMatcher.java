package io.github.kylinhunter.tools.dic.core.match.imp;

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
    protected DictionarySkipper dictionarySkipper = DictionarySkipper.getInstance();
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
        switch (findLevel) {
            case HIGH: {
                return process(text, FindLevel.HIGH, dictionaryGroup.getHighMiddleLow());
            }
            case HIGH_MIDDLE: {

                List<MatchResult> resultHigh = process(text, FindLevel.HIGH, dictionaryGroup.getHigh());
                List<MatchResult> resultMiddle = process(text, FindLevel.HIGH_MIDDLE, dictionaryGroup.getMiddleLow());
                return CollectionUtils.merge(true, resultHigh, resultMiddle);
            }
            case HIGH_MIDDLE_LOW: {

                List<MatchResult> resultHigh = process(text, FindLevel.HIGH, dictionaryGroup.getHigh());
                List<MatchResult> resultMiddle = process(text, FindLevel.HIGH_MIDDLE, dictionaryGroup.getMiddle());
                List<MatchResult> resultLow = process(text, FindLevel.HIGH_MIDDLE_LOW, dictionaryGroup.getLow());
                return CollectionUtils.merge(true, resultHigh, resultMiddle, resultLow);
            }
        }

        return null;

    }

    protected abstract List<MatchResult> process(String text, FindLevel high, Dictionary<WordNode> dictionary);
}
