package io.github.kylinhunter.tools.dic.core.match.bean;

import java.util.Arrays;
import java.util.StringJoiner;

import io.github.kylinhunter.tools.dic.core.dictionary.bean.WordNode;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.MatchLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class MatchResult<T extends WordNode> {
    private MatchLevel matchLevel;
    private String hitWord;
    private String hitWordRaw;
    private int start;
    private int end;
    private String[] assistedWords;
    private T wordNode;

    public MatchResult(MatchLevel matchLevel) {
        this.matchLevel = matchLevel;
    }

    public void setWordNode(T wordNode) {
        this.wordNode = wordNode;
        this.hitWordRaw = wordNode.getWord();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MatchResult.class.getSimpleName() + "[", "]")
                .add("matchLevel=" + matchLevel)
                .add("hitWord='" + hitWord + "'")
                .add("hitWordRaw='" + hitWordRaw + "'")
                .add("start=" + start)
                .add("end=" + end)
                .add("assistedWords=" + Arrays.toString(assistedWords))
                .toString();
    }
}
