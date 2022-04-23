
package com.kylinhunter.nlp.dic.core.dictionary.trie;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
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
     * @param word word
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
     * @param word word
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
        sb.append("\n count:").append(count).append(",maxLength:").append(maxLength).append("\n");
        wordNums.forEach((key, value) -> sb.append("word with length(").append(key).append(")  :")
                .append(value).append("\n"));
        sb.append("word average length:").append((float) totalLength / count).append("\n");
        return sb.toString();
    }

}
