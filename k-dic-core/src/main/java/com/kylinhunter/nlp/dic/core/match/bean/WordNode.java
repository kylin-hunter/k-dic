package com.kylinhunter.nlp.dic.core.match.bean;

import com.kylinhunter.nlp.dic.core.analyzer.bean.Words;
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

    private Words keywordSplit;

    private String[] assistWords;
    private Words[] assistWordsSplit;

    private String[] relationWords;

    public boolean hasAssistWords() {
        return (assistWords != null && assistWords.length > 0);
    }

}
