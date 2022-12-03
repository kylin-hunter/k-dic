package io.github.kylinhunter.tools.dic.core.dictionary.imp;

import io.github.kylinhunter.tools.dic.core.dictionary.constant.DictionaryConst;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.MatchLevel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.tools.dic.core.dictionary.bean.MatchContext;
import io.github.kylinhunter.tools.dic.core.trie.TrieStatHelper;

public class DictionaryTrieTest {

    private static final char SC = DictionaryConst.SPECIAL_CHAR;

    private void init(DictionaryTrie<Long> dictionaryTrie) {
        // 定义了三种匹配模式
        // 1、精确匹配，一模一样
        // 2、中度匹配、词中含有常见符号，
        // 3、低度匹配、含有常见符号之外，额外含有若干个其他字符，例如一个中英文字符

        dictionaryTrie.put("毕庄", 1L);
        dictionaryTrie.put("毕家庄", 2L);
        dictionaryTrie.put("毕继安", 3L);
        dictionaryTrie.put("毕平安", 4L);
        dictionaryTrie.put("胡庄", 5L);
        dictionaryTrie.put("罗小培", 6L);
        dictionaryTrie.put("罗小杰", 7L);
        dictionaryTrie.put("中华人民共和国", 8L);
        dictionaryTrie.put("中华", 9L);
        dictionaryTrie.put("毕继安", 10L);
        TrieStatHelper.showStatistics(dictionaryTrie);
        TrieStatHelper.showConfilicMessage(dictionaryTrie);
        TrieStatHelper.show(dictionaryTrie);
    }

    @Test
    public void test1() {
        DictionaryTrie<Long> dictionaryTrie = new DictionaryTrie();
        init(dictionaryTrie);
        MatchContext<Long> matchContext = new MatchContext<>();
        matchContext.findLevel = FindLevel.HIGH.getCode();

        dictionaryTrie.match("毕", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        dictionaryTrie.match("毕", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        dictionaryTrie.match("毕庄", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.HIGH.getCode());

        dictionaryTrie.match("毕庄1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        dictionaryTrie.match("毕继安", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.HIGH.getCode());

        dictionaryTrie.match("毕继安1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        dictionaryTrie.match("罗小培", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.HIGH.getCode());

        dictionaryTrie.match("罗小培1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

    }

    @Test
    public void test2() {
        DictionaryTrie<Long> dictionaryTrie = new DictionaryTrie();
        init(dictionaryTrie);
        MatchContext<Long> matchContext = new MatchContext<>();
        matchContext.findLevel = FindLevel.HIGH_MIDDLE.getCode();

        dictionaryTrie.match("罗小培", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.HIGH.getCode());
        dictionaryTrie.match("罗小培1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        dictionaryTrie.match("罗" + SC + SC + "小" + SC + SC + "培", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.MIDDLE.getCode());

        dictionaryTrie.match("罗" + SC + SC + "小" + SC + SC + "培1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        dictionaryTrie.setSkipMaxLen(2);
        dictionaryTrie.match("罗" + SC + SC + SC + SC + "小" + SC + SC + SC + SC + "培", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        dictionaryTrie.match("罗" + SC + SC + SC + SC + "小" + SC + SC + SC + SC + "培1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        dictionaryTrie.setSkipMaxLen(4);
        dictionaryTrie.match("罗" + SC + SC + SC + SC + "小" + SC + SC + SC + SC + "培", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.MIDDLE.getCode());

        dictionaryTrie.match("罗" + SC + SC + SC + SC + "小" + SC + SC + SC + SC + "培1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

    }

    @Test
    public void test3() {
        DictionaryTrie<Long> dictionaryTrie = new DictionaryTrie();
        init(dictionaryTrie);
        MatchContext<Long> matchContext = new MatchContext<>();

        matchContext.findLevel = FindLevel.HIGH_MIDDLE_LOW.getCode();
        dictionaryTrie.match("罗小培", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.HIGH.getCode());
        dictionaryTrie.match("罗小培1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);
        dictionaryTrie.match("罗" + SC + SC + "小" + SC + SC + "培", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.MIDDLE.getCode());
        dictionaryTrie.match("罗" + SC + SC + "小" + SC + SC + "培1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);
        dictionaryTrie.match("罗" + SC + SC + "小" + SC + SC + "1" + SC + SC + "培", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.LOW.getCode());
        dictionaryTrie.match("罗" + SC + SC + "小" + SC + SC + "1" + SC + SC + "培1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        dictionaryTrie.setSkipMaxLen(2);
        dictionaryTrie.match("罗" + SC + SC + SC + SC + "小" + SC + SC + SC + SC + "1培", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        dictionaryTrie.match("罗" + SC + SC + SC + SC + "小" + SC + SC + SC + SC + "1培1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        dictionaryTrie.setSkipMaxLen(4);
        dictionaryTrie.match("罗" + SC + SC + SC + SC + "小" + SC + SC + SC + SC + "1培", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.LOW.getCode());

        dictionaryTrie.match("罗" + SC + SC + SC + SC + "小" + SC + SC + SC + SC + "1培1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

    }
}
