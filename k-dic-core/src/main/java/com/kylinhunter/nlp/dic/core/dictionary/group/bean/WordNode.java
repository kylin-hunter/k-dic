package com.kylinhunter.nlp.dic.core.dictionary.group.bean;

import com.kylinhunter.nlp.dic.core.analyzer.bean.Words;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
public class WordNode {
    protected HitMode hitMode;
    protected String keyword;
}
