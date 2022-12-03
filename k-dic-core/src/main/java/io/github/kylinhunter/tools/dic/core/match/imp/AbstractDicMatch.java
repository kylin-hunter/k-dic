package io.github.kylinhunter.tools.dic.core.match.imp;

import java.util.List;

import io.github.kylinhunter.commons.util.CollectionUtils;
import io.github.kylinhunter.tools.dic.core.dictionary.Dictionary;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.group.DictionaryGroup;
import io.github.kylinhunter.tools.dic.core.match.DicMatch;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;
import io.github.kylinhunter.tools.dic.core.match.bean.WordNode;
import io.github.kylinhunter.tools.dic.core.match.component.DicSkipper;
import io.github.kylinhunter.tools.dic.words.analyzer.WordAnalyzer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractDicMatch implements DicMatch {
    protected DicSkipper dicSkipper = DicSkipper.getInstance();
    protected DictionaryGroup<WordNode> dictionaryGroup;
    protected WordAnalyzer analyzer;
    protected boolean assistMatchEnabled;

    public AbstractDicMatch(DictionaryGroup<WordNode> dictionaryGroup) {
        this.dictionaryGroup = dictionaryGroup;
        this.assistMatchEnabled = dictionaryGroup.isAssistMatchEnabled();
        this.analyzer = dictionaryGroup.getWordAnalyzer();
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
