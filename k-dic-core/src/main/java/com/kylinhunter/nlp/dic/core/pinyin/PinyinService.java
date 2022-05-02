package com.kylinhunter.nlp.dic.core.pinyin;

public interface PinyinService {

    public String toPinyin(char c);

    public String toPinyin(String chars);

    public String[] toPinyins(char c);

    public String[] toPinyins(String oriStr, int maxSize);

}