
package com.kylinhunter.nlp.dic.core.dictionary.trie;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022/1/1
 **/
@Getter
@Setter
public class TrieStat {
    Map<Integer, Integer> wordNums = new HashMap<>();
    public int count = 0;
    public int totalLength = 0;
    public int maxLength;

    /**
     * @param word
     * @return void
     * @throws
     * @title put
     * @description
     * @author BiJi'an
     * @updateTime 2022-04-16 02:13
     */
    public void put(String word) {
        int wordLen = word.length();
        wordNums.compute(wordLen, (k, v) -> {
            if (v == null) {
                return 1;
            } else {
                return v + 1;
            }
        });
        count++;
        if (wordLen > maxLength) {
            maxLength = wordLen;
        }
        totalLength += wordLen;
    }

    /**
     * @param word
     * @return void
     * @throws
     * @title remove
     * @description
     * @author BiJi'an
     * @updateTime 2022-04-16 02:13
     */
    public void remove(String word) {
        int wordLen = word.length();
        wordNums.compute(wordLen, (k, v) -> {
            if (v == null) {
                return 0;
            } else {
                return v - 1;
            }
        });
        count--;
        if (wordLen == maxLength) {
            int num = wordNums.get(wordLen);
            if (num <= 0) {
                maxLength--;
            }
        }
        totalLength -= word.length();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n count:" + count + ",maxLength:" + maxLength + "\n");
        wordNums.entrySet().forEach(e -> {
            sb.append("word with length(" + e.getKey() + ")  :" + e.getValue() + "\n");
        });
        sb.append("word average length:" + (float) totalLength / count + "\n");
        return sb.toString();
    }

}
