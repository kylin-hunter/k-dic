package com.kylinhunter.nlp.dic.core.match.bean;

import java.util.List;

import com.kylinhunter.nlp.dic.commons.util.EnumUtil;
import com.kylinhunter.nlp.dic.core.dictionary.constant.MatchLevel;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.WordNode;
import com.kylinhunter.nlp.dic.core.dictionary.trie.TrieNode;
import com.kylinhunter.nlp.dic.core.match.constant.FindType;

import lombok.Data;

@Data
public class DictionarySearch {

    private FindType findType;
    private String hitWord;
    private MatchLevel level;
    private TrieNode<MatchWordNode> node;
    private List<MatchWordNode> wordNodes;

    public DictionarySearch(FindType findType, String hitWord, int matchLevel, TrieNode<MatchWordNode> node) {
        this.findType = findType;
        this.hitWord = hitWord;
        this.level = EnumUtil.fromCode(MatchLevel.class, matchLevel);
        this.node = node;
        this.wordNodes = node.getValues();
    }

}
