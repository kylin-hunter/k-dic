package io.github.kylinhunter.tools.dic.core;

import java.util.List;

import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.match.DictionaryMatcher;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author bijian
 * @version 1.0
 * @description DicCache
 * @date 2022/1/22 1:12
 */
@AllArgsConstructor
@Data
public class Dic {

    private DictionaryMatcher dictionaryMatcher;

    public List<MatchResult> match(String inputText, FindLevel findLevel) {
        return dictionaryMatcher.match(inputText, findLevel);
    }

}


