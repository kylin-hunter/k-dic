package com.kylinhunter.nlp.dic.core.match.imp;

import java.util.List;

import com.kylinhunter.nlp.dic.commons.service.KServices;
import com.kylinhunter.nlp.dic.commons.util.CollectionUtil;
import com.kylinhunter.nlp.dic.core.analyzer.WordAnalyzer;
import com.kylinhunter.nlp.dic.core.dictionary.Dictionary;
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;
import com.kylinhunter.nlp.dic.core.dictionary.group.DictionaryGroup;
import com.kylinhunter.nlp.dic.core.match.DicMatch;
import com.kylinhunter.nlp.dic.core.match.bean.WordNode;
import com.kylinhunter.nlp.dic.core.match.bean.MatchResult;
import com.kylinhunter.nlp.dic.core.match.component.DicSkipper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractDicMatch implements DicMatch {
    protected DicSkipper dicSkipper = DicSkipper.getInstance();
    protected DictionaryGroup<WordNode> dictionaryGroup;
    protected WordAnalyzer analyzer;
    protected boolean assistMatch;
    protected int prefixMatchMaxNum;

    public AbstractDicMatch(DictionaryGroup<WordNode> dictionaryGroup) {
        this.dictionaryGroup = dictionaryGroup;
        this.assistMatch = dictionaryGroup.getDicConfig().isAssistMatch();
        this.prefixMatchMaxNum = dictionaryGroup.getDicConfig().getPrefixMatchMaxNum();
        this.analyzer = KServices.get(dictionaryGroup.getConfig().getWordAnalyzer());
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
                return CollectionUtil.merge(resultHigh, resultMiddle);
            }
            case HIGH_MIDDLE_LOW: {

                List<MatchResult> resultHigh = process(text, FindLevel.HIGH, dictionaryGroup.getHigh());
                List<MatchResult> resultMiddle = process(text, FindLevel.HIGH_MIDDLE, dictionaryGroup.getMiddle());
                List<MatchResult> resultLow = process(text, FindLevel.HIGH_MIDDLE_LOW, dictionaryGroup.getLow());
                return CollectionUtil.merge(resultHigh, resultMiddle, resultLow);
            }
        }

        return null;

    }

    protected abstract List<MatchResult> process(String text, FindLevel high, Dictionary<WordNode> dictionary);
}
