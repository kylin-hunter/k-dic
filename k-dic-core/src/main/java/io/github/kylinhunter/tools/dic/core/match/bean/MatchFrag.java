package io.github.kylinhunter.tools.dic.core.match.bean;

import io.github.kylinhunter.commons.util.EnumUtils;
import io.github.kylinhunter.tools.dic.core.dictionary.Dictionary.MatchContext;
import io.github.kylinhunter.tools.dic.core.dictionary.bean.WordNode;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.MatchLevel;
import io.github.kylinhunter.tools.dic.core.trie.TrieNode;
import lombok.Data;

@Data
public class MatchFrag<T extends WordNode> {

    private MatchLevel level;
    private int start;
    private int end;
    private String hitWord;
    private TrieNode<T> node;

    public MatchFrag(String text, int start, int len, MatchContext<T> matchContext) {

        this.start = start;
        this.end = start + len;
        this.hitWord = text.substring(start, start + len);
        this.level = EnumUtils.fromCode(MatchLevel.class, matchContext.matchLevel);
        this.node = matchContext.node;
    }

    public MatchFrag(String text, int start, int len, TrieNode<T> node) {

        this.start = start;
        this.end = start + len;
        this.hitWord = text.substring(start, start + len);
        this.level = MatchLevel.NONE;
        this.node = node;
    }
}
