package com.kylinhunter.nlp.dic.core.dic.bean;

import java.util.List;

import com.kylinhunter.nlp.dic.commons.util.EnumUtil;
import com.kylinhunter.nlp.dic.core.dictionary.constant.MatchLevel;

import lombok.Data;

@Data
public class DictionarySearch {

    private String hitWord;
    private MatchLevel level;
    private List<MatchWordNode> wordNodes;
    private boolean secondaryWordsMatch;

    public DictionarySearch(String hitWord, int matchLevel, List<MatchWordNode> wordNodes, boolean secondaryWordsMatch) {
        this.hitWord = hitWord;
        this.level = EnumUtil.fromCode(MatchLevel.class, matchLevel);
        this.wordNodes = wordNodes;
        this.secondaryWordsMatch = secondaryWordsMatch;
    }

}
