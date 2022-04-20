package com.kylinhunter.nlp.dic.core.dictionary.imp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.kylinhunter.nlp.dic.commons.service.SimpleServiceFactory;
import com.kylinhunter.nlp.dic.core.dictionary.DictionaryType;
import com.kylinhunter.nlp.dic.core.dictionary.bean.FindContext;
import com.kylinhunter.nlp.dic.core.dictionary.imp.DictionaryTrie;
import com.kylinhunter.nlp.dic.core.dictionary.trie.Trie;
import com.kylinhunter.nlp.dic.core.dictionary.trie.TrieHelper;

public class DictionaryTrieTest {

    @Test
    void test1() {
        final Trie<Integer> trie = new Trie<Integer>();
        trie.put("毕庄", 1);
        trie.put("毕家庄", 1);
        trie.put("毕继安", 1);
        trie.put("毕平安", 1);
        trie.put("胡庄", 1);
        trie.put("罗小培", 1);
        trie.put("罗小杰", 1);
        trie.put("中华人民共和国", 1);
        trie.put("中华", 1);
        TrieHelper.showConfilicMessage(trie);
        TrieHelper.show(trie);
        Assertions.assertEquals(false, trie.contains("毕"));
        Assertions.assertEquals(true, trie.contains("毕庄"));
        Assertions.assertEquals(true, trie.contains("毕继安"));
        Assertions.assertEquals(true, trie.contains("罗小培"));

        trie.remove("罗小培");
        Assertions.assertEquals(false, trie.contains("罗小培"));
        TrieHelper.showConfilicMessage(trie);
        TrieHelper.show(trie);

    }

    @Test
    public void test2() {

        // 定义了三种匹配模式
        // 1、精确匹配，一模一样
        // 2、中度匹配、词中含有常见符号，
        // 3、低度匹配、含有常见符号之外，额外含有一个其他字符，例如一个中英文字符

        DictionaryTrie<Long> trie = SimpleServiceFactory.create(DictionaryType.TRIE);
        FindContext<Long> findContext = new FindContext<Long>();
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
        trie.find("毕", findContext);
        System.out.println("findContext.matchLevel:" + findContext.matchLevel);
        Assertions.assertEquals(true, findContext.matchLevel == 0);
        trie.find("毕庄", findContext);
        Assertions.assertEquals(true, findContext.matchLevel == 1);
        trie.find("毕继安", findContext);
        Assertions.assertEquals(true, findContext.matchLevel == 1);

        trie.find("罗小培", findContext);
        Assertions.assertEquals(true, findContext.matchLevel == 1);

        findContext.findLevel = 3;
        trie.find("罗``小``培", findContext);
        System.out.println("findContext.matchLevel:" + findContext.matchLevel);

        Assertions.assertEquals(true, findContext.matchLevel == 2);

        trie.find("罗``1小1``培", findContext);
        System.out.println("findContext.matchLevel:" + findContext.matchLevel);

        Assertions.assertEquals(true, findContext.matchLevel == 3);

        TrieHelper.showStatistics(trie);
        TrieHelper.showConfilicMessage(trie);
        TrieHelper.show(trie);

    }
}
