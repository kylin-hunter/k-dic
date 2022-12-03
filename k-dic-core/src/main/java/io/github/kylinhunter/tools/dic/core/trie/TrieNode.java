
package io.github.kylinhunter.tools.dic.core.trie;

import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

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
     * @date 2022-12-03 18:59
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
     * @param character character
     * @return io.github.kylinhunter.tools.dic.core.trie.TrieNode<T>
     * @title getChild
     * @description
     * @author BiJi'an
     * @date 2022-12-03 18:59
     */
    public TrieNode<T> getChild(char character) {
        int index = Arrays.binarySearch(childrenChar, character);
        if (index >= 0) {
            return children[index];
        }
        return null;
    }

    /**
     * @param childChar childChar
     * @return io.github.kylinhunter.tools.dic.core.trie.TrieNode<T>
     * @title addChild
     * @description
     * @author BiJi'an
     * @date 2022-12-03 19:00
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
        return new StringJoiner(", ", TrieNode.class.getSimpleName() + "[", "]")
                .add("character=" + character)
                .add("terminal=" + terminal)
                .add("sibling=" + sibling)
                .add("childrenChar=" + Arrays.toString(childrenChar))
                .add("children=" + children.length)
                .add("values=" + values)
                .toString();
    }
}
