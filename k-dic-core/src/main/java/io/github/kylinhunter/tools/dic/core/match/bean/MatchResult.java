package io.github.kylinhunter.tools.dic.core.match.bean;

import io.github.kylinhunter.tools.dic.core.dictionary.constant.MatchLevel;

import io.github.kylinhunter.tools.dic.core.dictionary.bean.WordNode;
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
    private String hitWord;
    private String matchWord;
    private int start;
    private int end;
    private String[] assistWords;
    private WordNode wordNode;


    public MatchResult(int type, int classId, int matchLevel) {
        this.type = type;
        this.classId = classId;
        this.matchLevel = matchLevel;
    }

    public MatchResult(int type, int classId, MatchLevel matchLevel) {
        this.type = type;
        this.classId = classId;
        this.matchLevel = matchLevel.getCode();
    }

    public void setWordNode(WordNode wordNode) {
        this.wordNode = wordNode;
        this.matchWord = wordNode.getKeyword();
    }
}
