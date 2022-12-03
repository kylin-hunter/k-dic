package io.github.kylinhunter.tools.dic.core.dic;

import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.match.DicMatch;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author bijian
 * @version 1.0
 * @description DicCache
 * @date 2022/1/22 1:12
 */
@AllArgsConstructor
@Data
public class Dic {
    private DicMatch dicMatch;

    public List<MatchResult> match(String inputText, FindLevel findLevel) {
        return dicMatch.match(inputText, findLevel);
    }

}


