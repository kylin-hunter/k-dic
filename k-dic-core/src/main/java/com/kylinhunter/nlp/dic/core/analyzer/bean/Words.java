package com.kylinhunter.nlp.dic.core.analyzer.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import lombok.Getter;
import lombok.Setter;

/**
 * @description: a group words
 * @author: BiJi'an
 * @create: 2022/1/1
 **/
@Getter
@Setter
public class Words {
    private List<Word> words = new ArrayList<>();

    /**
     * @param word
     * @return void
     * @throws
     * @title add a word
     * @description
     * @author BiJi'an
     * @updateTime 2022/1/1 11:22 下午
     */
    public void addWord(Word word) {
        words.add(word);
    }

    /***
     * @title add a word
     * @description
     * @author BiJi'an
     * @param text
     * @param statOffset
     * @param endOffset
     * @updateTime 2022/1/1 11:22 下午
     * @return void
     * @throws
     */
    public void addWord(String text, int statOffset, int endOffset) {
        words.add(new Word(text, statOffset, endOffset));
    }

    /**
     * @param action
     * @return void
     * @throws
     * @title traversal words
     * @description
     * @author BiJi'an
     * @updateTime 2022/1/1 11:22 下午
     */
    public void forEach(Consumer<Word> action) {
        words.forEach(action);
    }

    /**
     * @param text
     * @return boolean
     * @throws
     * @title test the words wether contains text
     * @description
     * @author BiJi'an
     * @updateTime 2022/1/1 11:22 下午
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
     * @throws
     * @title the size of words
     * @description
     * @author BiJi'an
     * @updateTime 2022/1/1 3:39
     */
    public int size() {
        return words.size();
    }

}
