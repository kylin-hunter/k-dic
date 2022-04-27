package com.kylinhunter.nlp.dic.core.match;

import java.util.List;

import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;
import com.kylinhunter.nlp.dic.core.dictionary.group.DictionaryGroup;
import com.kylinhunter.nlp.dic.core.match.bean.MatchResult;

public interface DicMatch {
    List<MatchResult> match(String inputText, FindLevel findLevel);

    DictionaryGroup getDictionaryGroup();

}
