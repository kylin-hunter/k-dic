package io.github.kylinhunter.tools.dic.core.dictionary.bean;

import io.github.kylinhunter.tools.dic.core.dictionary.constant.HitMode;
import io.github.kylinhunter.tools.dic.words.analyzer.bean.Words;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(of = {"type", "classId", "assistedWords", "targetWords"})
public class WordNode {
    protected HitMode hitMode;  // hit mode

    @EqualsAndHashCode.Include
    protected String word; // key word
    @EqualsAndHashCode.Include
    protected int type;
    @EqualsAndHashCode.Include
    protected int classId;

    protected String[] assistedWords;  // the  words for auxiliary judging hit
    protected String[] targetWords; // the targed words

    private Words analyzedWords; // the analyzed  words for word
    private Words[] analyzedAssistedWords; // the analyzed  words for assistedWords

    public boolean hasAssistedKeywords() {
        return (assistedWords != null && assistedWords.length > 0);
    }

}
