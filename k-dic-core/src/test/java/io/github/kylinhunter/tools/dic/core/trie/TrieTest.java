package io.github.kylinhunter.tools.dic.core.trie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TrieTest {

    @Test
    void test() {

        final Trie<?> trie = new Trie<>();
        trie.put("毕庄");
        trie.put("毕家庄");
        trie.put("毕继安");
        trie.put("毕平安");
        trie.put("胡庄");
        trie.put("罗小培");
        trie.put("罗小杰");
        trie.put("中华人民共和国");
        trie.put("中华");
        TrieStatHelper.showConfilicMessage(trie);
        TrieStatHelper.show(trie);
        Assertions.assertFalse(trie.contains("毕"));
        Assertions.assertTrue(trie.contains("毕庄"));
        Assertions.assertTrue(trie.contains("毕继安"));
        Assertions.assertTrue(trie.contains("罗小培"));

        trie.remove("罗小培");
        Assertions.assertFalse(trie.contains("罗小培"));
        TrieStatHelper.showConfilicMessage(trie);
        TrieStatHelper.show(trie);
    }

    @Test
    void test2() {

        final Trie<Integer> trie = new Trie<>();
        trie.put("毕庄", 99);
        trie.put("毕家庄", 99);
        trie.put("毕继安", 99);
        trie.put("毕平安", 99);
        trie.put("胡庄", 99);
        trie.put("罗小培", 99);
        trie.put("罗小杰", 99);
        trie.put("中华人民共和国", 99);
        trie.put("中华", 99);
        TrieStatHelper.showConfilicMessage(trie);
        TrieStatHelper.show(trie);
        Assertions.assertFalse(trie.contains("毕"));
        Assertions.assertTrue(trie.contains("毕庄"));
        Assertions.assertTrue(trie.contains("毕继安"));
        Assertions.assertTrue(trie.contains("罗小培"));

        Assertions.assertEquals(99, trie.getValue("罗小培"));
        trie.remove("罗小培");
        Assertions.assertFalse(trie.contains("罗小培"));
        Assertions.assertNull(trie.getValue("罗小培"));
        TrieStatHelper.showConfilicMessage(trie);
        TrieStatHelper.show(trie);
    }
}