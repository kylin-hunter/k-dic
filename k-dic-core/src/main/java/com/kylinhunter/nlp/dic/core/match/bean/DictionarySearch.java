package com.kylinhunter.nlp.dic.core.match.bean;

import java.util.List;

import com.kylinhunter.nlp.dic.commons.util.EnumUtil;
import com.kylinhunter.nlp.dic.core.dictionary.bean.MatchContext;
import com.kylinhunter.nlp.dic.core.dictionary.constant.MatchLevel;
import com.kylinhunter.nlp.dic.core.dictionary.trie.TrieNode;

import lombok.Data;

@Data
public class DictionarySearch {

    private String hitWord;
    private int start;
    private int end;
    private MatchLevel level;
    private TrieNode<MatchWordNode> node;
    private List<MatchWordNode> wordNodes;

    public DictionarySearch(String text, int start, int len, MatchContext matchContext) {

        this.start = start;
        this.end = start + len;
        this.hitWord = text.substring(start, start + len);
        this.level = EnumUtil.fromCode(MatchLevel.class, matchContext.matchLevel);
        this.node = matchContext.node;
        this.wordNodes = node.getValues();
    }

}
