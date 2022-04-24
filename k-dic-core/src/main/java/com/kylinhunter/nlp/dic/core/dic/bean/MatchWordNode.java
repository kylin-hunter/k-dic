package com.kylinhunter.nlp.dic.core.dic.bean;

import com.kylinhunter.nlp.dic.core.analyzer.bean.Words;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.WordNode;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = {"type", "classId"}, callSuper = true)
@ToString(of = {"type", "classId", "secondaryWords", "relationWords"}, callSuper = true)
public class MatchWordNode extends WordNode {
    private int type;
    private int classId;

    private Words keywordSplit;

    private String[] secondaryWords;
    private Words[] secondaryWordsSplit;

    private String[] relationWords;

    public boolean hasSecondaryWords() {
        return (secondaryWords != null && secondaryWords.length > 0);
    }

}
