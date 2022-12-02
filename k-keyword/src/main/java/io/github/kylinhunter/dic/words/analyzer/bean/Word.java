package io.github.kylinhunter.dic.words.analyzer.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description  a hitWord
 * @author  BiJi'an
 * @date 2022/1/1
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Word implements Serializable {
    private String text; //  content
    private int startOffset; //  start position
    private int endOffset; // end position

    public Word(String text) {
        this.text = text;
    }

    public boolean eq(String text) {
        return this.text.equals(text);
    }
}
