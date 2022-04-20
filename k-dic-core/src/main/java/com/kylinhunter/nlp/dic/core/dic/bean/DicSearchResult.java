package com.kylinhunter.nlp.dic.core.dic.bean;

import java.util.List;

import com.kylinhunter.nlp.dic.commons.util.EnumUtil;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.WordNode;
import com.kylinhunter.nlp.dic.core.dictionary.constant.MatchLevel;

import lombok.Data;

@Data
public class DicSearchResult {

    private String hitWord;
    private MatchLevel level;
    private List<WordNode> wordNodes;
    private boolean secondaryWordsMatch = false;

    public DicSearchResult(String hitWord, int matchLevel, List<WordNode> wordNodes, boolean secondaryWordsMatch) {
        this.hitWord = hitWord;
        this.level = EnumUtil.fromCode(MatchLevel.class, matchLevel);
        this.wordNodes = wordNodes;
        this.secondaryWordsMatch = secondaryWordsMatch;
    }

}
