
package io.github.kylinhunter.tools.dic.core.dictionary.trie;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022/1/1
 **/
@NoArgsConstructor
@Getter
@Setter
public class Trie<T> {
    private static final int MAX_WORD_LENGTH = 200;
    public static final int FIRST_CHAR_INDEX_SLOT_NUM = 24000;
    @SuppressWarnings("unchecked")
    private final TrieNode<T>[] firstCharIndex = new TrieNode[FIRST_CHAR_INDEX_SLOT_NUM];
    private final TrieStat trieStat = new TrieStat();

    public TrieNode<T>[] getFirstCharIndex() {
        return firstCharIndex;
    }

    private int maxLength = 2;

    /**
     * @param character character
     * @return com.kylinhunter.nlp.Config.core.Config.single.trie.TrieNode<T>
     * @title addRootNode
     * @description
     * @author BiJi'an
     * @date 2022-01-16 02:26
     */
    private TrieNode<T> addRootNode(char character) {
        TrieNode<T> rootNode = getRootNode(character);
        if (rootNode == null) {
            rootNode = new TrieNode<>(character);
            int index = rootNode.getCharacter() % FIRST_CHAR_INDEX_SLOT_NUM;
            TrieNode<T> existRootNode = firstCharIndex[index];
            if (existRootNode != null) {
                rootNode.setSibling(existRootNode);
            }
            firstCharIndex[index] = rootNode;
        }
        return rootNode;
    }

    /**
     * @param character character
     * @return com.kylinhunter.nlp.Config.core.Config.single.trie.TrieNode<T>
     * @title getRootNode
     * @description
     * @author BiJi'an
     * @date 2022-01-16 02:26
     */
    public TrieNode<T> getRootNode(char character) {
        int index = character % FIRST_CHAR_INDEX_SLOT_NUM;
        TrieNode<T> trieNode = firstCharIndex[index];
        while (trieNode != null && character != trieNode.getCharacter()) {
            trieNode = trieNode.getSibling();
        }
        return trieNode;
    }

    /**
     * @param item item
     * @param t value
     * @return boolean
     * @title put
     * @description
     * @author BiJi'an
     * @date 2022-01-16 02:26
     */
    @SuppressWarnings("UnusedReturnValue")
    public boolean put(String item, T t) {
        item = item.trim();
        int len = item.length();
        if (len < 1 || len > MAX_WORD_LENGTH) {
            return false;
        }
        TrieNode<T> node = addRootNode(item.charAt(0));
        for (int i = 1; i < len; i++) {
            node = node.addChild(item.charAt(i));
        }
        this.addValue(node, t);
        if (node.isTerminal()) {
            return false;
        }
        node.setTerminal(true);
        if (len > maxLength) {
            maxLength = len;
        }
        trieStat.put(item);
        return true;

    }

    /**
     * @param node node
     * @title addValue
     * @description
     * @author BiJi'an
     * @date 2022-01-16 02:29
     */
    private void addValue(TrieNode<T> node, T t) {
        if (t != null) {
            List<T> oldValues = node.getValues();
            if (oldValues == null) {
                oldValues = new ArrayList<>();
                node.setValues(oldValues);
                oldValues.add(t);
            } else {
                if (!oldValues.contains(t)) {
                    oldValues.add(0, t);
                }
            }
        }
    }

    /**
     * @param item item
     * @title remove
     * @description
     * @author BiJi'an
     * @date 2022-01-16 02:30
     */
    public void remove(String item) {
        TrieNode<T> node = getNode(item);
        if (node != null && node.isTerminal()) {
            node.setTerminal(false);
            node.setValues(null);
            trieStat.remove(item);
        }

    }

    /**
     * @param item item
     * @return boolean
     * @title contains
     * @description
     * @author BiJi'an
     * @date 2022-01-16 02:46
     */
    public boolean contains(String item) {
        TrieNode<T> node = getNode(item);
        return node != null && node.isTerminal();
    }

    /**
     * @param item item
     * @return java.util.List<T>
     * @title get
     * @description
     * @author BiJi'an
     * @date 2022-01-16 02:47
     */
    public List<T> getValues(String item) {
        TrieNode<T> node = getNode(item);
        if (node != null && node.isTerminal()) {
            return node.getValues();
        }
        return null;
    }

    /**
     * @param item item
     * @return java.util.List<T>
     * @title get
     * @description
     * @author BiJi'an
     * @date 2022-01-16 02:47
     */
    public T getValue(String item) {
        TrieNode<T> node = getNode(item);
        if (node != null && node.isTerminal()) {
            List<T> values = node.getValues();
            if (values != null && values.size() > 0) {
                return values.get(0);
            }
        }
        return null;
    }

    /**
     * @return int
     * @title size
     * @description
     * @author BiJi'an
     * @date 2022-01-16 02:55
     */
    public int size() {
        return trieStat.count;
    }

    /**
     * @return int
     * @title getMaxLength
     * @description
     * @author BiJi'an
     * @date 2022-01-16 02:56
     */
    public int getMaxLength() {
        return trieStat.getMaxLength();
    }

    /**
     * @param item item
     * @return com.kylinhunter.nlp.Config.core.Config.single.trie.TrieNode<T>
     * @title getNode
     * @description
     * @author BiJi'an
     * @date 2022-01-16 02:56
     */
    private TrieNode<T> getNode(String item) {
        if (StringUtils.isEmpty(item)) {
            return null;
        }
        TrieNode<T> node = getRootNode(item.charAt(0));
        if (node == null) {
            return null;
        }
        for (int i = 1; i < item.length(); i++) {
            TrieNode<T> child = node.getChild(item.charAt(i));
            if (child == null) {
                return null;
            } else {
                node = child;
            }
        }
        return node;
    }

}
