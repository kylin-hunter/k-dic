package com.kylinhunter.nlp.dic.core.match.bean;

import com.kylinhunter.nlp.dic.core.dictionary.bean.MatchContext;
import com.kylinhunter.nlp.dic.core.dictionary.constant.MatchLevel;
import com.kylinhunter.nlp.dic.core.dictionary.trie.TrieNode;
import com.kylinhunter.plat.commons.util.EnumUtil;

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
        this.level = EnumUtil.fromCode(MatchLevel.class, matchContext.matchLevel);
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
