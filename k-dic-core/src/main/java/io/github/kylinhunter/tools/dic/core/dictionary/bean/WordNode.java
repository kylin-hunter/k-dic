package io.github.kylinhunter.tools.dic.core.dictionary.bean;

import io.github.kylinhunter.tools.dic.core.dictionary.constant.HitMode;
import io.github.kylinhunter.tools.dic.words.analyzer.bean.Words;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(of = {"type", "classId", "assistWords", "relationWords"}, callSuper = true)
public class WordNode {
    protected HitMode hitMode;  // hit mode
    @EqualsAndHashCode.Include
    protected String keyword; // key word
    @EqualsAndHashCode.Include
    private int type;
    @EqualsAndHashCode.Include
    private int classId;
    private String[] assistWords;
    private String[] relationWords;

    private Words keywordSplit;
    private Words[] assistWordsSplit;

    public boolean hasAssistWords() {
        return (assistWords != null && assistWords.length > 0);
    }

}
