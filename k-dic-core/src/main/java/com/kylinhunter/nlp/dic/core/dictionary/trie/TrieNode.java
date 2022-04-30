
package com.kylinhunter.nlp.dic.core.dictionary.trie;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
@Getter
@Setter
@RequiredArgsConstructor
public class TrieNode<T> {
    private final char character;
    private boolean terminal;
    private TrieNode<T> sibling;
    private char[] childrenChar = new char[0];
    @SuppressWarnings("unchecked")
    private TrieNode<T>[] children = new TrieNode[0];
    private List<T> values = null;

    /**
     * @return int
     * @title getSiblingNum
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-16 01:58
     */
    public int getSiblingNum() {
        TrieNode<T> node = this;
        int i = 0;
        while ((node = node.getSibling()) != null) {
            i++;
        }
        return i;
    }

    /**
     * getChild
     *
     * @param character character
     */
    public TrieNode<T> getChild(char character) {
        int index = Arrays.binarySearch(childrenChar, character);
        if (index >= 0) {
            return children[index];
        }
        return null;
    }

    /**
     * addChild
     *
     * @param childChar childChar
     */
    @SuppressWarnings("unchecked")
    public TrieNode<T> addChild(char childChar) {
        TrieNode<T> childNode = getChild(childChar);
        if (childNode == null) {
            childNode = new TrieNode<>(childChar);
            int length = childrenChar.length;
            if (length == 0) {
                childrenChar = new char[1];
                childrenChar[0] = childChar;
                children = new TrieNode[1];
                children[0] = childNode;

            } else {
                char[] newChildrenChar = new char[length + 1];
                TrieNode<T>[] newChildrenNode = new TrieNode[length + 1];
                int i;
                for (i = 0; i < length; i++) {
                    if (childChar <= childrenChar[i]) {
                        newChildrenNode[i] = childNode;
                        System.arraycopy(children, i, newChildrenNode, i + 1, length - i);
                        newChildrenChar[i] = childChar;
                        System.arraycopy(childrenChar, i, newChildrenChar, i + 1, length - i);
                        break;
                    } else {
                        newChildrenNode[i] = children[i];
                        newChildrenChar[i] = childrenChar[i];
                    }
                }
                if (i >= length) {
                    newChildrenNode[length] = childNode;
                    newChildrenChar[length] = childChar;
                }
                children = newChildrenNode;
                childrenChar = newChildrenChar;
            }
        }
        return childNode;
    }

    @Override
    public String toString() {
        return "TrieNode{" +
                "character=" + character +
                ", terminal=" + terminal +
                ", sibling=" + sibling +
                ", childrenChar=" + Arrays.toString(childrenChar) +
                ", children=" + children.length +
                ", values=" + values +
                '}';
    }
}
