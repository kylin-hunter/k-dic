package com.kylinhunter.nlp.dic.core.match.bean;

import com.kylinhunter.nlp.dic.core.analyzer.bean.Words;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.WordNode;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = {"type", "classId"}, callSuper = true)
@ToString(of = {"type", "classId", "assistWords", "relationWords"}, callSuper = true)
public class MatchWordNode extends WordNode {
    private int type;
    private int classId;

    private Words keywordSplit;

    private String[] assistWords;
    private Words[] assistWordsSplit;

    private String[] relationWords;

    public boolean hasSecondaryWords() {
        return (assistWords != null && assistWords.length > 0);
    }

}
