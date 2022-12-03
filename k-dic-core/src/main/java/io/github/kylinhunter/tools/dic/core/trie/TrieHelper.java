
package io.github.kylinhunter.tools.dic.core.trie;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
@Slf4j
public class TrieHelper {

    /**
     * @param parent  parent
     * @param maxSize maxSize
     * @return java.util.List<io.github.kylinhunter.tools.dic.core.trie.TrieNode < T>>
     * @title prefix
     * @description
     * @author BiJi'an
     * @date 2022-12-03 18:58
     */
    public static <T> List<TrieNode<T>> prefix(TrieNode<T> parent, int maxSize) {
        List<TrieNode<T>> result = new ArrayList<>();
        TrieHelper.prefix(parent, result, maxSize);
        return result;
    }

    /**
     * @param parent  parent
     * @param result  result
     * @param maxSize maxSize
     * @return void
     * @title prefix
     * @description
     * @author BiJi'an
     * @date 2022-12-03 18:58
     */
    private static <T> void prefix(TrieNode<T> parent, List<TrieNode<T>> result, int maxSize) {
        if (result.size() >= maxSize) {
            return;
        }
        if (parent.isTerminal()) {
            result.add(parent);
        }
        TrieNode<T>[] children = parent.getChildren();
        if (children != null) {
            for (TrieNode<T> child : children) {
                prefix(child, result, maxSize);
            }
        }

    }
}
