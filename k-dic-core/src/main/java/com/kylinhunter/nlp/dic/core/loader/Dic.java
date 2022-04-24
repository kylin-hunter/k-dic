package com.kylinhunter.nlp.dic.core.loader;

import com.kylinhunter.nlp.dic.core.analyzer.WordAnalyzer;
import com.kylinhunter.nlp.dic.core.dic.DicMatch;
import com.kylinhunter.nlp.dic.core.dic.bean.MatchResult;
import com.kylinhunter.nlp.dic.core.dic.option.MatchOption;
import com.kylinhunter.nlp.dic.core.dictionary.group.DictionaryGroup;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author bijian
 * @version 1.0
 * @description DicCache
 * @date 2022/4/22 1:12
 */
@AllArgsConstructor
@Data
public class Dic {
    private DicMatch dicMatch;

    public List<MatchResult> match(String inputText, MatchOption matchOption) {
        return dicMatch.match(inputText, matchOption);
    }

}


