
package com.kylinhunter.nlp.dic.core.dictionary.trie;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2022/1/1
 **/
@Slf4j
public class TrieHelper {

    /**
     * @param trie
     * @return void
     * @throws
     * @title showStatistics
     * @description
     * @author BiJi'an
     * @updateTime 2022-04-16 02:01
     */
    public static <T> void showStatistics(Trie<T> trie) {
        log.debug(trie.getTrieStat().toString());

    }

    /**
     * @param trie
     * @return void
     * @throws
     * @title showConfilicMessage
     * @description
     * @author BiJi'an
     * @updateTime 2022-04-16 02:01
     */
    public static <T> void showConfilicMessage(Trie<T> trie) {
        StringBuilder sb = new StringBuilder();
        int emptySlot = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (TrieNode<T> node : trie.getFirstCharIndex()) {
            if (node == null) {
                emptySlot++;
            } else {
                int siblingNum = node.getSiblingNum();
                if (siblingNum > 0) {
                    map.compute(siblingNum, (k, v) -> {
                        if (v == null) {
                            return 1;
                        } else {
                            v++;
                            return v;
                        }
                    });
                }
            }
        }
        int count = 0;
        for (int key : map.keySet()) {
            int value = map.get(key);
            count += key * value;
            sb.append("there is  " + value + " slot  whose conflict Num is " + key + "\n");
        }
        int firstCharIndexSlotNum = Trie.FIRST_CHAR_INDEX_SLOT_NUM;
        sb.append("all conflict num:" + count + "\n");
        sb.append("usage rate:(" + (firstCharIndexSlotNum - emptySlot) + "/" + firstCharIndexSlotNum + ")="
                + (float) (firstCharIndexSlotNum - emptySlot) / firstCharIndexSlotNum * 100 + "%" + "\n");
        sb.append("remaining:" + emptySlot + "\n");

        log.debug("\n " + sb.toString());
    }

    /**
     * @param trie
     * @param character
     * @return void
     * @throws
     * @title show
     * @description
     * @author BiJi'an
     * @updateTime 2022-04-16 02:06
     */
    public static <T> void show(Trie<T> trie, char character) {
        StringBuilder sb = new StringBuilder();
        show(sb, trie.getRootNode(character), "");
        log.debug("\n" + sb.toString());
    }

    /**
     * @param trie
     * @return void
     * @throws
     * @title show
     * @description
     * @author BiJi'an
     * @updateTime 2022-04-16 02:07
     */
    public static <T> void show(Trie<T> trie) {
        StringBuilder sb = new StringBuilder();
        for (TrieNode<T> node : trie.getFirstCharIndex()) {
            if (node != null) {
                show(sb, node, "");
            }
        }
        log.debug("\n" + sb.toString());
    }

    /**
     * @param node
     * @param indent
     * @return void
     * @throws
     * @title show
     * @description
     * @author BiJi'an
     * @updateTime 2022-04-16 02:07
     */
    private static <T> void show(StringBuilder sb, TrieNode<T> node, String indent) {
        if (node.isTerminal()) {
            sb.append(indent + node.getCharacter() + ":" + node.getValues() + "(T) \n");
        } else {
            sb.append(indent + node.getCharacter() + ":" + node.getValues() + "\n");
        }
        for (TrieNode<T> item : node.getChildren()) {
            show(sb, item, indent + "\t");
        }
    }
}
