package com.kylinhunter.nlp.dic.core.dic;

import java.util.List;

import com.kylinhunter.nlp.dic.core.analyzer.WordAnalyzer;
import com.kylinhunter.nlp.dic.core.dictionary.group.DictionaryGroup;
import com.kylinhunter.nlp.dic.core.dic.bean.MatchResult;
import com.kylinhunter.nlp.dic.core.dic.option.MatchOption;

public interface DicMatch {
    List<MatchResult> match(String inputText, MatchOption matchOption);

    DictionaryGroup getDictionaryGroup();

    DicMatch init(DictionaryGroup dictionaryGroup, WordAnalyzer wordAnalyzer);
}
