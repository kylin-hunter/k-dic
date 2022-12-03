
package io.github.kylinhunter.tools.dic.core.trie;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
@Getter
@Setter
public class Trie<T> {
    private int wordLenLimit;
    private int firstCharIndexSlotNum;
    private TrieNode<T>[] firstCharIndex;
    private TrieStat trieStat = new TrieStat();

    public Trie() {
        this(TireConst.DEFAULT_MAX_WORD_LENGTH, TireConst.DEFAULT_FIRST_CHAR_INDEX_SLOT_NUM);
    }

    @SuppressWarnings("unchecked")
    public Trie(int wordLenLimit, int firstCharIndexSlotNum) {
        this.wordLenLimit = wordLenLimit;
        this.firstCharIndexSlotNum = firstCharIndexSlotNum;
        this.firstCharIndex = new TrieNode[firstCharIndexSlotNum];

    }

    /**
     * @param character character
     * @return io.github.kylinhunter.tools.dic.core.trie.TrieNode<T>
     * @title addRootNode
     * @description
     * @author BiJi'an
     * @date 2022-12-03 18:44
     */
    private TrieNode<T> addRootNode(char character) {
        TrieNode<T> rootNode = getRootNode(character);
        if (rootNode == null) {
            rootNode = new TrieNode<>(character);
            int index = rootNode.getCharacter() % firstCharIndexSlotNum;
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
     * @return io.github.kylinhunter.tools.dic.core.trie.TrieNode<T>
     * @title getRootNode
     * @description
     * @author BiJi'an
     * @date 2022-12-03 18:44
     */
    public TrieNode<T> getRootNode(char character) {
        int index = character % firstCharIndexSlotNum;
        TrieNode<T> trieNode = firstCharIndex[index];
        while (trieNode != null && character != trieNode.getCharacter()) {
            trieNode = trieNode.getSibling();
        }
        return trieNode;
    }

    /**
     * @param item item
     * @return boolean
     * @title put
     * @description
     * @author BiJi'an
     * @date 2022-12-03 19:17
     */
    public boolean put(String item) {
        return this.put(item, null);
    }

    /**
     * @param item item
     * @param t    t
     * @return boolean
     * @title put
     * @description
     * @author BiJi'an
     * @date 2022-12-03 18:44
     */
    public boolean put(String item, T t) {
        item = item.trim();
        int len = item.length();
        if (len < 1 || len > wordLenLimit) {
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
        trieStat.put(item);
        return true;

    }

    /**
     * @param node node
     * @param t    t
     * @return void
     * @title addValue
     * @description
     * @author BiJi'an
     * @date 2022-12-03 18:45
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
     * @return void
     * @title remove
     * @description
     * @author BiJi'an
     * @date 2022-12-03 18:45
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
     * @date 2022-12-03 18:45
     */
    public boolean contains(String item) {
        TrieNode<T> node = getNode(item);
        return node != null && node.isTerminal();
    }

    /**
     * @param item item
     * @return java.util.List<T>
     * @title getValues
     * @description
     * @author BiJi'an
     * @date 2022-12-03 18:45
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
     * @return T
     * @title getValue
     * @description
     * @author BiJi'an
     * @date 2022-12-03 18:45
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
     * @date 2022-12-03 18:45
     */
    public int size() {
        return trieStat.wordNums;
    }

    /**
     * @return int
     * @title getCurMaxLength
     * @description
     * @author BiJi'an
     * @date 2022-12-03 18:48
     */
    public int getWordMaxLen() {
        return trieStat.getWordMaxLen();
    }

    /**
     * @param item item
     * @return io.github.kylinhunter.tools.dic.core.trie.TrieNode<T>
     * @title getNode
     * @description
     * @author BiJi'an
     * @date 2022-12-03 18:46
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
