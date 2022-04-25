package com.kylinhunter.nlp.dic.core.match.bean;

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
    private String[] assistWords;
    private WordNode matchWordNode;

    public MatchResult(int type, int classId, int matchLevel) {
        this.type = type;
        this.classId = classId;
        this.matchLevel = matchLevel;
    }

}
