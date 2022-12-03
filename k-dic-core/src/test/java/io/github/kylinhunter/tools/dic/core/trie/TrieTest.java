package io.github.kylinhunter.tools.dic.core.trie;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TrieTest {

    @Test
    void test() {
        // test trie
        final Trie<?> trie = new Trie<>();
        trie.put("毕庄");  // add word
        trie.put("毕家庄");
        trie.put("毕继安");
        trie.put("毕平安");
        trie.put("海淀");
        trie.put("海淀黄庄");
        trie.put("中华人民共和国");
        trie.put("中华");
        trie.put("中华民族");
        TrieStatHelper.showConfilicMessage(trie);
        TrieStatHelper.show(trie);
        Assertions.assertFalse(trie.contains("毕")); // word  no exist
        Assertions.assertTrue(trie.contains("毕庄"));  // word   exist
        Assertions.assertTrue(trie.contains("毕继安")); // word   exist
        Assertions.assertTrue(trie.contains("海淀黄庄")); // word   exist
        trie.remove("海淀黄庄"); // remove word
        Assertions.assertFalse(trie.contains("海淀黄庄"));  // word  no exist
        TrieStatHelper.showConfilicMessage(trie);
        TrieStatHelper.show(trie);
    }

    @Test
    void test2() {
        // test trie with value
        Trie<Integer> trie = new Trie<>();
        trie.put("毕庄", 1); //add word with a value 1
        trie.put("毕家庄", 2); //add word with a value 2
        trie.put("毕继安", 3);
        trie.put("毕平安", 4);
        trie.put("北京", 5);
        trie.put("海淀", 6);
        trie.put("海淀黄庄", 7); //add word  海淀黄庄 with a value 7
        trie.put("海淀黄庄", 8);  //add word 海淀黄庄 with a value 8
        trie.put("中华人民共和国", 9);
        trie.put("中华", 10);
        trie.put("中华民族", 11);
        TrieStatHelper.showConfilicMessage(trie);
        TrieStatHelper.show(trie);
        Assertions.assertFalse(trie.contains("毕")); // word  no exist
        Assertions.assertTrue(trie.contains("毕庄")); // word    exist
        Assertions.assertTrue(trie.contains("毕继安")); // word    exist
        Assertions.assertTrue(trie.contains("海淀黄庄")); // word    exist

        Assertions.assertEquals(10, trie.getValue("中华")); // get value =10 
        Assertions.assertEquals(Arrays.asList(8, 7), trie.getValues("海淀黄庄")); // get two value  8 and 7
        trie.remove("海淀黄庄");
        Assertions.assertFalse(trie.contains("海淀黄庄")); // word  no exist
        Assertions.assertNull(trie.getValue("海淀黄庄")); // get value  is null
        TrieStatHelper.showConfilicMessage(trie);
        TrieStatHelper.show(trie);
    }
}