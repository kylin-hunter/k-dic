package com.kylinhunter.nlp.dic.core.dictionary.trie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrieTest {

    @Test
    void test() {

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
}