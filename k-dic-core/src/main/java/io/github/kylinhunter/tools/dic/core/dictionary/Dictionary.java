
package io.github.kylinhunter.tools.dic.core.dictionary;

import io.github.kylinhunter.tools.dic.core.dictionary.constant.DicConst;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.MatchLevel;
import io.github.kylinhunter.tools.dic.core.trie.Trie;
import io.github.kylinhunter.tools.dic.core.trie.TrieNode;
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
public class Dictionary<T> extends Trie<T> {
    private int skipMaxLen = 2;

    /**
     * @param text         text
     * @param matchContext matchContext
     * @return void
     * @title match
     * @description
     * @author BiJi'an
     * @date 2022-12-04 01:13
     */

    public void match(String text, MatchContext<T> matchContext) {
        this.match(text.toCharArray(), 0, text.length(), matchContext);
    }

    /**
     * @param word         word
     * @param start        start
     * @param length       length
     * @param matchContext matchContext
     * @return void
     * @title match
     * @description
     * @author BiJi'an
     * @date 2022-12-04 01:13
     */
    public void match(char[] word, int start, int length, MatchContext<T> matchContext) {
        matchContext.matchLevel = DicConst.MATCH_LEVEL_NONE;
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
                if (matchContext.findLevel <= DicConst.FIND_LEVEL_HIGH) {
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
                            if (matchContext.matchLevel < DicConst.MATCH_LEVEL_LOW) {
                                matchContext.matchLevel = DicConst.MATCH_LEVEL_MIDDLE;
                            }
                            resultNode = child;
                            findLoc++;

                        } else {

                            if (matchContext.findLevel != DicConst.FIND_LEVEL_LOW) {
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
                                    matchContext.matchLevel = DicConst.MATCH_LEVEL_LOW;
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

            matchContext.node = resultNode;
            if (!resultNode.isTerminal()) {
                matchContext.matchLevel = DicConst.MATCH_LEVEL_NONE;
            } else {
                if (matchContext.matchLevel < DicConst.MATCH_LEVEL_MIDDLE) {
                    matchContext.matchLevel = DicConst.MATCH_LEVEL_HIGH;
                }
            }
        } else {
            matchContext.matchLevel = DicConst.MATCH_LEVEL_NONE;
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
     * @date 2022-01-16 03:04
     */
    private int nextValidChar(char[] word, int start, int end) {
        int num = 0;
        while (start < end) {
            if (word[start] == DicConst.SPECIAL_CHAR) {
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

    @NoArgsConstructor
    public static class MatchContext<T> {

        /**
         * @see FindLevel
         */
        public int findLevel = FindLevel.HIGH.getCode();

        /**
         * @see MatchLevel
         */
        public int matchLevel = MatchLevel.NONE.getCode();

        /*tire node */
        public TrieNode<T> node;

        public MatchContext(FindLevel findLevel) {
            this.findLevel = findLevel.getCode();
        }
    }

}
