
package io.github.kylinhunter.tools.dic.core.trie;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
@Slf4j
public class TrieStatHelper {

    /**
     * @param trie trie
     * @title showStatistics
     * @description
     * @author BiJi'an
     * @date 2022-01-16 02:01
     */
    public static <T> void showStatistics(Trie<T> trie) {
        log.info(trie.getTrieStat().toString());

    }

    /**
     * @param trie trie
     * @title showConfilicMessage
     * @description
     * @author BiJi'an
     * @date 2022-01-16 02:01
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
            sb.append("there is  ").append(value).append(" slot  whose conflict Num is ").append(key).append("\n");
        }
        int firstCharIndexSlotNum = trie.getFirstCharIndexSlotNum();
        sb.append("all conflict num:").append(count).append("\n");
        sb.append("usage rate:(").append(firstCharIndexSlotNum - emptySlot).append("/");
        sb.append(firstCharIndexSlotNum).append(")=");
        sb.append((float) (firstCharIndexSlotNum - emptySlot) / firstCharIndexSlotNum * 100).append("%").append("\n");
        sb.append("remaining:").append(emptySlot).append("\n");

        log.info("\n" + sb);
    }

    /**
     * @param trie      trie
     * @param character character
     * @title show
     * @description
     * @author BiJi'an
     * @date 2022-01-16 02:06
     */
    public static <T> void show(Trie<T> trie, char character) {
        StringBuilder sb = new StringBuilder();
        show(sb, trie.getRootNode(character), "");
        log.info("\n" + sb);
    }

    /**
     * @param trie trie
     * @title show
     * @description
     * @author BiJi'an
     * @date 2022-01-16 02:07
     */
    public static <T> void show(Trie<T> trie) {
        StringBuilder sb = new StringBuilder();
        for (TrieNode<T> node : trie.getFirstCharIndex()) {
            if (node != null) {
                show(sb, node, "");
            }
        }
        log.info("\n" + sb);
    }

    /**
     * @param node   node
     * @param indent indent
     * @title show
     * @description
     * @author BiJi'an
     * @date 2022-01-16 02:07
     */
    private static <T> void show(StringBuilder sb, TrieNode<T> node, String indent) {
        if (node.isTerminal()) {
            sb.append(indent).append(node.getCharacter()).append(":").append(node.getValues()).append("(T) \n");
        } else {
            sb.append(indent).append(node.getCharacter()).append(":").append(node.getValues()).append("\n");
        }
        for (TrieNode<T> item : node.getChildren()) {
            show(sb, item, indent + "\t");
        }
    }
}
