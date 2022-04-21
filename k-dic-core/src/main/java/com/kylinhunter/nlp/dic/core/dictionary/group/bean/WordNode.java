package com.kylinhunter.nlp.dic.core.dictionary.group.bean;

import com.kylinhunter.nlp.dic.core.analyzer.bean.Words;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = {"type", "classId", "hitMode", "keyword"})
@ToString(of = {"type", "classId", "hitMode", "keyword", "secondaryWords", "relationWords"})
public class WordNode {
    private int type;
    private int classId;
    private HitMode hitMode;

    private String keyword;
    private Words keywordSplit;

    private String[] secondaryWords;
    private Words[] secondaryWordsSplit;

    private String[] relationWords;






    public boolean hasSecondaryWords() {
        return (secondaryWords != null && secondaryWords.length > 0);
    }

}
