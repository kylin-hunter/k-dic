package com.kylinhunter.nlp.dic.core.dictionary.imp;

import com.kylinhunter.nlp.dic.core.dictionary.constant.DictionaryConst;
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;
import com.kylinhunter.nlp.dic.core.dictionary.constant.MatchLevel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.kylinhunter.nlp.dic.commons.service.KServices;
import com.kylinhunter.nlp.dic.core.dictionary.DictionaryType;
import com.kylinhunter.nlp.dic.core.dictionary.bean.MatchContext;
import com.kylinhunter.nlp.dic.core.dictionary.trie.TrieHelper;

public class DictionaryTrieTest {

    static DictionaryTrie<Long> trie = KServices.create(DictionaryType.TRIE);
    private static final char SC = DictionaryConst.SPECIAL_CHAR;

    @BeforeAll
    public static void init() {
        // 定义了三种匹配模式
        // 1、精确匹配，一模一样
        // 2、中度匹配、词中含有常见符号，
        // 3、低度匹配、含有常见符号之外，额外含有若干个其他字符，例如一个中英文字符


        trie.put("毕庄", 1L);
        trie.put("毕家庄", 2L);
        trie.put("毕继安", 3L);
        trie.put("毕平安", 4L);
        trie.put("胡庄", 5L);
        trie.put("罗小培", 6L);
        trie.put("罗小杰", 7L);
        trie.put("中华人民共和国", 8L);
        trie.put("中华", 9L);
        trie.put("毕继安", 10L);
        TrieHelper.showStatistics(trie);
        TrieHelper.showConfilicMessage(trie);
        TrieHelper.show(trie);
    }

    @Test
    public void test1() {

        MatchContext<Long> matchContext = new MatchContext<>();
        matchContext.findLevel = FindLevel.HIGH.getCode();

        trie.match("毕", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        trie.match("毕", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);


        trie.match("毕庄", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.HIGH.getCode());

        trie.match("毕庄1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);


        trie.match("毕继安", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.HIGH.getCode());

        trie.match("毕继安1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);


        trie.match("罗小培", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.HIGH.getCode());

        trie.match("罗小培1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);


    }

    @Test
    public void test2() {
        MatchContext<Long> matchContext = new MatchContext<>();
        matchContext.findLevel = FindLevel.HIGH_MIDDLE.getCode();

        trie.match("罗小培", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.HIGH.getCode());
        trie.match("罗小培1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        trie.match("罗" + SC + SC + "小" + SC + SC + "培", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.MIDDLE.getCode());

        trie.match("罗" + SC + SC + "小" + SC + SC + "培1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        matchContext.maxSkip = 2;
        trie.match("罗" + SC + SC + SC + SC + "小" + SC + SC + SC + SC + "培", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        trie.match("罗" + SC + SC + SC + SC + "小" + SC + SC + SC + SC + "培1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        matchContext.maxSkip = 4;
        trie.match("罗" + SC + SC + SC + SC + "小" + SC + SC + SC + SC + "培", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.MIDDLE.getCode());

        trie.match("罗" + SC + SC + SC + SC + "小" + SC + SC + SC + SC + "培1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);


    }

    @Test
    public void test3() {
        MatchContext<Long> matchContext = new MatchContext<>();

        matchContext.findLevel = FindLevel.HIGH_MIDDLE_LOW.getCode();
        trie.match("罗小培", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.HIGH.getCode());
        trie.match("罗小培1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);
        trie.match("罗" + SC + SC + "小" + SC + SC + "培", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.MIDDLE.getCode());
        trie.match("罗" + SC + SC + "小" + SC + SC + "培1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);
        trie.match("罗" + SC + SC + "小" + SC + SC + "1" + SC + SC + "培", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.LOW.getCode());
        trie.match("罗" + SC + SC + "小" + SC + SC + "1" + SC + SC + "培1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        matchContext.maxSkip = 2;
        trie.match("罗" + SC + SC + SC + SC + "小" + SC + SC + SC + SC + "1培", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        trie.match("罗" + SC + SC + SC + SC + "小" + SC + SC + SC + SC + "1培1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        matchContext.maxSkip = 4;
        trie.match("罗" + SC + SC + SC + SC + "小" + SC + SC + SC + SC + "1培", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.LOW.getCode());

        trie.match("罗" + SC + SC + SC + SC + "小" + SC + SC + SC + SC + "1培1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);


    }
}
