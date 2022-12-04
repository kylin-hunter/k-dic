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
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class AbstractDictionaryMatcher implements DictionaryMatcher {
    protected final DictionarySkipper dictionarySkipper;
    protected DictionaryGroup dictionaryGroup = new DictionaryGroup();
    protected WordAnalyzer analyzer = CF.get(WordAnalyzerType.DEFAULT);
    protected boolean assistMatchEnabled = true;


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

    /**
     * @param wordNode wordNode
     * @return void
     * @title addWord
     * @description
     * @author BiJi'an
     * @date 2022-12-04 19:57
     */
    public void addWord(WordNode wordNode) {
        this.dictionaryGroup.put(wordNode);
    }

    protected abstract List<MatchResult> process(String text, FindLevel high, Dictionary<WordNode> dictionary);
}
