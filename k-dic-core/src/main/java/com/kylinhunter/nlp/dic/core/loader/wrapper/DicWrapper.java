package com.kylinhunter.nlp.dic.core.loader.wrapper;

import com.kylinhunter.nlp.dic.core.dic.Dic;
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
public class DicWrapper implements Dic {
    private Dic dic;

    @Override
    public List<MatchResult> match(String inputText, MatchOption matchOption) {
        return dic.match(inputText, matchOption);
    }

    @Override
    public DictionaryGroup getDictionaryGroup() {
        return dic.getDictionaryGroup();
    }
}


