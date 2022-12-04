package io.github.kylinhunter.tools.dic.core.dictionary.bean;

import io.github.kylinhunter.tools.dic.core.dictionary.constant.HitMode;
import io.github.kylinhunter.tools.dic.words.analyzer.bean.Words;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(of = {"type", "classId", "assistedKeywords", "targetWords"})
public class WordNode {

    @EqualsAndHashCode.Include
    protected String keyword; // key word
    @EqualsAndHashCode.Include
    private int type;
    @EqualsAndHashCode.Include
    private int classId;

    protected HitMode hitMode;  // hit mode

    private String[] assistedKeywords;  // the  words for auxiliary judging hit
    private String[] targetWords; // the targed words

    private Words analyzedKeywords; // the analyzed  words for keyword
    private Words[] analyzedRelatedKeywords; // the analyzed  words for assistedKeywords

    public boolean hasAssistedKeywords() {
        return (assistedKeywords != null && assistedKeywords.length > 0);
    }

}
