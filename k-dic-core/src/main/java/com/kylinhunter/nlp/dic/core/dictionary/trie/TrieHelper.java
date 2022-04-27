
package com.kylinhunter.nlp.dic.core.dictionary.trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.kylinhunter.nlp.dic.core.match.bean.MatchWordNode;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
@Slf4j
public class TrieHelper {

    public static <T> List<T> prefix(TrieNode<T> parent, int maxSize) {
        List<T> result = new ArrayList<>();
        TrieHelper.prefix(parent, result, maxSize);
        return result;
    }

    public static <T> void prefix(TrieNode<T> parent, List<T> result, int maxSize) {
        if (result.size() >= maxSize) {
            return;
        }
        if (parent.isTerminal()) {
            result.addAll(parent.getValues());
        }
        TrieNode<T>[] children = parent.getChildren();
        if (children != null) {
            for (TrieNode<T> child : children) {
                prefix(child, result, maxSize);
            }
        }

    }
}
