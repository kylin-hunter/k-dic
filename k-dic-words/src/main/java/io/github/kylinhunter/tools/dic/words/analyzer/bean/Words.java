package io.github.kylinhunter.tools.dic.words.analyzer.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import lombok.Getter;
import lombok.Setter;

/**
 * @description  a group words
 * @author  BiJi'an
 * @date 2022/1/1
 **/
@Getter
@Setter
public class Words {
    private List<Word> words = new ArrayList<>();

    /**
     * @param word hitWord
     * @title add a hitWord
     * @description
     * @author BiJi'an
     * @date 2022/1/1 11:22 下午
     */
    public void addWord(Word word) {
        words.add(word);
    }

    /***
     * @title add a hitWord
     * @description
     * @author BiJi'an
     * @param text text
     * @param statOffset statOffset
     * @param endOffset endOffset
     * @date 2022/1/1 11:22 下午
     */
    public void addWord(String text, int statOffset, int endOffset) {
        words.add(new Word(text, statOffset, endOffset));
    }

    /**
     * @param action action
     * @title traversal words
     * @description
     * @author BiJi'an
     * @date 2022/1/1 11:22 下午
     */
    public void forEach(Consumer<Word> action) {
        words.forEach(action);
    }

    /**
     * @param text text
     * @return boolean
     * @title test the words wether contains text
     * @description
     * @author BiJi'an
     * @date 2022/1/1 11:22 下午
     */
    public boolean contains(String text) {
        for (Word word : words) {
            if (word.eq(text)) {
                return true;
            }
        }
        return false;

    }

    /**
     * @return int
     * @title the size of words
     * @description
     * @author BiJi'an
     * @date 2022/1/1 3:39
     */
    public int size() {
        return words.size();
    }

}
