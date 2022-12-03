package io.github.kylinhunter.tools.dic.core.match.bean;

import io.github.kylinhunter.tools.dic.core.dictionary.bean.MatchContext;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.MatchLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.trie.TrieNode;
import io.github.kylinhunter.commons.util.EnumUtils;

import lombok.Data;

@Data
public class DictionarySearch {

    private MatchLevel level;
    private int start;
    private int end;
    private String hitWord;
    private TrieNode<WordNode> node;

    public DictionarySearch(String text, int start, int len, MatchContext matchContext) {

        this.start = start;
        this.end = start + len;
        this.hitWord = text.substring(start, start + len);
        this.level = EnumUtils.fromCode(MatchLevel.class, matchContext.matchLevel);
        this.node = matchContext.node;
    }

    public DictionarySearch(String text, int start, int len, TrieNode<WordNode> node) {

        this.start = start;
        this.end = start + len;
        this.hitWord = text.substring(start, start + len);
        this.level = MatchLevel.NONE;
        this.node = node;
    }
}
