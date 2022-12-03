
package io.github.kylinhunter.tools.dic.core.trie;

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
    Map<Integer, Integer> wordLenCounter = new HashMap<>();
    public int wordNums = 0;
    public int totalLength = 0;
    public int wordMaxLen;

    /**
     * @param word hitWord
     * @title put
     * @description
     * @author BiJi'an
     * @date 2022-01-16 02:13
     */
    public void put(String word) {
        int wordLen = word.length();
        wordLenCounter.compute(wordLen, (k, v) -> {
            if (v == null) {
                return 1;
            } else {
                return v + 1;
            }
        });
        wordNums++;
        if (wordLen > wordMaxLen) {
            wordMaxLen = wordLen;
        }
        totalLength += wordLen;
    }

    /**
     * @param word hitWord
     * @title remove
     * @description
     * @author BiJi'an
     * @date 2022-01-16 02:13
     */
    public void remove(String word) {
        int wordLen = word.length();
        wordLenCounter.compute(wordLen, (k, v) -> {
            if (v == null) {
                return 0;
            } else {
                return v - 1;
            }
        });
        wordNums--;
        if (wordLen == wordMaxLen) {
            int num = wordLenCounter.get(wordLen);
            if (num <= 0) {
                wordMaxLen--;
            }
        }
        totalLength -= word.length();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n wordNums:").append(wordNums).append(",curMaxLength:").append(wordMaxLen).append("\n");
        wordLenCounter.forEach((key, value) -> sb.append("hitWord with length(").append(key).append(")  :")
                .append(value).append("\n"));
        sb.append("hitWord average length:").append((float) totalLength / wordNums).append("\n");
        return sb.toString();
    }

}
