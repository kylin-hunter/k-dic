package io.github.kylinhunter.tools.dic.core.match;

import java.util.List;

import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.group.DictionaryGroup;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;

public interface DicMatch {
    List<MatchResult> match(String inputText, FindLevel findLevel);

    DictionaryGroup getDictionaryGroup();

}
