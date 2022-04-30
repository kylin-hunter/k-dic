
package com.kylinhunter.nlp.dic.core.dictionary.imp;

import com.kylinhunter.nlp.dic.core.dictionary.Dictionary;
import com.kylinhunter.nlp.dic.core.dictionary.bean.MatchContext;
import com.kylinhunter.nlp.dic.core.dictionary.constant.DictionaryConst;
import com.kylinhunter.nlp.dic.core.dictionary.trie.Trie;
import com.kylinhunter.nlp.dic.core.dictionary.trie.TrieNode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DictionaryTrie<T> extends Trie<T> implements Dictionary<T> {
    private int skipMaxLen = 2;

    @Override
    public void match(String text, MatchContext<T> matchContext) {
        this.match(text.toCharArray(), 0, text.length(), matchContext);
    }

    @Override
    public void match(char[] word, int start, int length, MatchContext<T> matchContext) {
        matchContext.matchLevel = 0;
        matchContext.node = null;
        if (start < 0 || length < 1) {
            return;
        }
        int end = start + length;
        if (word == null || word.length < end) {
            return;
        }
        TrieNode<T> resultNode = getRootNode(word[start]);

        if (resultNode == null) {
            return;
        }
        int findLoc = start + 1;
        while (findLoc < end) {
            char character = word[findLoc];
            TrieNode<T> child = resultNode.getChild(character);
            if (child != null) {
                resultNode = child;
                findLoc++;
            } else {
                if (matchContext.findLevel <= DictionaryConst.FIND_LEVEL_HIGH) {
                    resultNode = null;
                    break;
                } else {
                    findLoc = nextValidChar(word, findLoc, end);
                    if (findLoc < 0) {
                        resultNode = null;
                        break;
                    } else {
                        character = word[findLoc];
                        child = resultNode.getChild(character);
                        if (child != null) {
                            if (matchContext.matchLevel < DictionaryConst.MATCH_LEVEL_LOW) {
                                matchContext.matchLevel = DictionaryConst.MATCH_LEVEL_MIDDLE;
                            }
                            resultNode = child;
                            findLoc++;

                        } else {

                            if (matchContext.findLevel != DictionaryConst.FIND_LEVEL_LOW) {
                                resultNode = null;
                                break;
                            }

                            findLoc++;
                            findLoc = nextValidChar(word, findLoc, end);
                            if (findLoc < 0) {
                                resultNode = null;
                                break;
                            } else {

                                character = word[findLoc];
                                child = resultNode.getChild(character);

                                if (child != null) {
                                    matchContext.matchLevel = DictionaryConst.MATCH_LEVEL_LOW;
                                    resultNode = child;
                                    findLoc++;

                                } else {
                                    resultNode = null;
                                    break;
                                }
                            }

                        }
                    }

                }

            }
        }

        if (resultNode != null) {
            if (matchContext.matchLevel < DictionaryConst.MATCH_LEVEL_MIDDLE) {
                matchContext.matchLevel = DictionaryConst.MATCH_LEVEL_HIGH;
            }
            matchContext.node = resultNode;
            if (!resultNode.isTerminal()) {
                matchContext.matchLevel = 0;
            }
        } else {
            matchContext.matchLevel = 0;
        }

    }

    /**
     * @param word  hitWord
     * @param start start
     * @param end   end
     * @return int
     * @title nextValidChar
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-16 03:04
     */
    private int nextValidChar(char[] word, int start, int end) {
        int num = 0;
        while (start < end) {
            if (word[start] == DictionaryConst.SPECIAL_CHAR) {
                start++;
                num++;
                if (num > skipMaxLen) {
                    break;
                }
            } else {
                return start;
            }
        }
        return -1;
    }

}
