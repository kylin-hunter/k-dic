package io.github.kylinhunter.tools.dic.app.imp;

import java.util.List;

import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.exception.embed.ParamException;
import io.github.kylinhunter.tools.dic.app.Dic;
import io.github.kylinhunter.tools.dic.app.bean.DicWord;
import io.github.kylinhunter.tools.dic.app.constant.DicType;
import io.github.kylinhunter.tools.dic.core.dictionary.bean.WordNode;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.match.DictionaryMatcher;
import io.github.kylinhunter.tools.dic.core.match.MatchType;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;
import io.github.kylinhunter.tools.dic.core.match.imp.FullDictionaryMatcher;
import io.github.kylinhunter.tools.dic.core.match.imp.PrefixDictionaryMatcher;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-04 23:29
 **/
public class AbstractDic<T extends DicWord> implements Dic<T> {
    private final DictionaryMatcher dictionaryMatcher;

    public AbstractDic(DicType dicType) {
        MatchType matchType = dicType.getMatchType();
        if (matchType == MatchType.FULL) {
            dictionaryMatcher = new FullDictionaryMatcher();
        } else if (matchType == MatchType.PREFIX) {
            dictionaryMatcher = new PrefixDictionaryMatcher();
        } else {
            throw new InitException("unsupported matchType=" + matchType);
        }

    }

    public void addWord(T word) {
        if (word instanceof WordNode) {
            dictionaryMatcher.addWord((WordNode) word);
        } else {
            throw new ParamException("word not a sub class of WordNode.class=>" + word.getClass().getName());
        }

    }

    public List<MatchResult> match(String inputText, FindLevel findLevel) {
        return dictionaryMatcher.match(inputText, findLevel);
    }
}
