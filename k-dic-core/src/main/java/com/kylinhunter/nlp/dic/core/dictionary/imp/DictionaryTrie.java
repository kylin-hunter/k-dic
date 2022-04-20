
package com.kylinhunter.nlp.dic.core.dictionary.imp;

import com.kylinhunter.nlp.dic.core.dictionary.Dictionary;
import com.kylinhunter.nlp.dic.core.dictionary.bean.FindContext;
import com.kylinhunter.nlp.dic.core.dictionary.component.FindSkipper;
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;
import com.kylinhunter.nlp.dic.core.dictionary.constant.MatchLevel;
import com.kylinhunter.nlp.dic.core.dictionary.trie.Trie;
import com.kylinhunter.nlp.dic.core.dictionary.trie.TrieNode;

import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2022/1/1
 **/
@Slf4j
public class DictionaryTrie<T> extends Trie<T> implements Dictionary<T> {

    private static final int MATCH_LEVEL_HIGH = MatchLevel.HIGH.getCode();
    private static final int MATCH_LEVEL_MIDDLE = MatchLevel.MIDDLE.getCode();
    private static final int MATCH_LEVEL_LOW = MatchLevel.LOW.getCode();

    private static final int FIND_LEVEL_HIGH = FindLevel.HIGH.getCode();
    private static final int FIND_LEVEL_MIDDLE = FindLevel.HIGH_MIDDLE.getCode();
    private static final int FIND_LEVEL_LOW = FindLevel.HIGH_MIDDLE_LOW.getCode();

    @Override
    public void find(String text, FindContext<T> dicResult) {
        this.find(text.toCharArray(), 0, text.length(), dicResult);
    }

    @Override
    public void find(char[] word, int start, int length, FindContext<T> findContext) {
        findContext.matchLevel = 0;
        findContext.node = null;
        if (start < 0 || length < 1) {
            return;
        }
        int end = start + length;
        if (word == null || word.length < end) {
            return;
        }
        TrieNode<T> node = getRootNode(word[start]);

        if (node == null) {
            return;
        }
        for (int i = 1; i < length; i++) {
            char character = word[i + start];
            TrieNode<T> child = node.getChild(character);
            if (child == null) {
                if (findContext.findLevel > FIND_LEVEL_HIGH) {
                    int nextCharIndex = nextValidChar(word, start + i, end, findContext);
                    if (nextCharIndex > 0) {
                        character = word[nextCharIndex];
                        child = node.getChild(character);
                        if (child == null) {
                            if (findContext.findLevel < FIND_LEVEL_LOW) {
                                node = null;
                                break;
                            }

                            if (nextCharIndex + 1 < end) {
                                character = word[nextCharIndex + 1];
                                child = node.getChild(character);
                            }
                            if (child == null) {
                                node = null;
                                break;
                            } else {
                                findContext.matchLevel = MATCH_LEVEL_LOW;
                            }

                        } else {
                            if (findContext.matchLevel < MATCH_LEVEL_LOW) {
                                findContext.matchLevel = MATCH_LEVEL_MIDDLE;
                            }
                        }
                    } else {
                        node = null;
                        break;
                    }

                } else {
                    node = null;
                    break;
                }

            } else {
                node = child;
            }
        }

        if (node != null && node.isTerminal()) {

            if (findContext.matchLevel < MATCH_LEVEL_MIDDLE) {
                findContext.matchLevel = MATCH_LEVEL_HIGH;
            }
            findContext.node = node;
        }
    }

    /**
     * @param word
     * @param start
     * @param end
     * @return int
     * @throws
     * @title nextValidChar
     * @description
     * @author BiJi'an
     * @updateTime 2022-04-16 03:04
     */
    private int nextValidChar(char[] word, int start, int end, FindContext<T> findContext) {
        int num = 0;
        while (start < end) {
            if (word[start] == FindSkipper.SPECIAL_CHAR) {
                start++;
                num++;
                if (num > findContext.maxSkip) {
                    break;
                }
            } else {
                return start;
            }
        }
        return -1;
    }

}
