package com.kylinhunter.nlp.dic.core.dic.bean;

import com.kylinhunter.nlp.dic.core.dictionary.group.bean.WordNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class MatchResult {

    private int type;
    private int classId;
    private int matchLevel;
    private String word;
    private String[] sencondaryWords;
    private WordNode wordNode;

    public MatchResult(int type, int classId, int matchLevel) {
        this.type = type;
        this.classId = classId;
        this.matchLevel = matchLevel;
    }

}
