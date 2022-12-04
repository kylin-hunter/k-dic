package io.github.kylinhunter.tools.dic.core.match;

import java.util.List;

import io.github.kylinhunter.tools.dic.core.dictionary.bean.WordNode;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;

public interface DictionaryMatcher {
    void addWord(WordNode wordNode);

    List<MatchResult> match(String inputText, FindLevel findLevel);
}
