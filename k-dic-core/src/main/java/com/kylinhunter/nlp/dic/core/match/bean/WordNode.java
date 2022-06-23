package com.kylinhunter.nlp.dic.core.match.bean;

import com.kylinhunter.nlp.dic.core.words.analyzer.bean.Words;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.DictionaryNode;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = {"type", "classId"}, callSuper = true)
@ToString(of = {"type", "classId", "assistWords", "relationWords"}, callSuper = true)
public class WordNode extends DictionaryNode {
    private int type;
    private int classId;
    private String[] assistWords;
    private String[] relationWords;

    private Words keywordSplit;
    private Words[] assistWordsSplit;



    public boolean hasAssistWords() {
        return (assistWords != null && assistWords.length > 0);
    }

}
