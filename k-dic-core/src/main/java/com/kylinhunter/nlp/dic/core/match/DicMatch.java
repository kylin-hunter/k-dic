package com.kylinhunter.nlp.dic.core.match;

import java.util.List;

import com.kylinhunter.nlp.dic.core.analyzer.WordAnalyzer;
import com.kylinhunter.nlp.dic.core.dictionary.group.DictionaryGroup;
import com.kylinhunter.nlp.dic.core.match.bean.MatchResult;
import com.kylinhunter.nlp.dic.core.match.option.MatchOption;

public interface DicMatch {
    List<MatchResult> match(String inputText, MatchOption matchOption);

    DictionaryGroup getDictionaryGroup();

}
