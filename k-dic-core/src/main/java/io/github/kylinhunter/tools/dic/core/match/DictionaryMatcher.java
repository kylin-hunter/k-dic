package io.github.kylinhunter.tools.dic.core.match;

import java.util.List;

import io.github.kylinhunter.tools.dic.core.dictionary.DictionaryGroup;
import io.github.kylinhunter.tools.dic.core.dictionary.WordNodeAware;
import io.github.kylinhunter.tools.dic.core.dictionary.bean.WordNode;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;
import io.github.kylinhunter.tools.dic.words.analyzer.WordAnalyzer;

public interface DictionaryMatcher<T extends WordNode, R extends WordNodeAware> {
    void addWord(T wordNode);

    void setWordAnalyzer(WordAnalyzer wordAnalyzer);

    void setDictionaryGroup(DictionaryGroup<T> dictionaryGroup);

    List<MatchResult<R>> match(String inputText, FindLevel findLevel);
}
