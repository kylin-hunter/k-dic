package com.kylinhunter.nlp.dic.core.pinyin;

/**
 * @author BiJi'an
 * @description
 * @create 2022/1/3
 **/
public interface PinyinService {

    String toPinyin(char c);

    String toPinyin(String chars);

    String[] toPinyins(char c);

    String[] toPinyins(String oriStr, int maxSize);

}