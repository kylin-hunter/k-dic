package io.github.kylinhunter.tools.dic.core.match;

import java.util.List;

import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;

public interface DictionaryMatcher {
    List<MatchResult> match(String inputText, FindLevel findLevel);
}
