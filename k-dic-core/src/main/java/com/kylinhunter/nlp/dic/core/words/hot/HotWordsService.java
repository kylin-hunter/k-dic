package com.kylinhunter.nlp.dic.core.words.hot;

import java.util.List;
import java.util.Map;

/**
 * @author BiJi'an
 * @description
 * @date 2022-05-14 14:37
 **/
public interface HotWordsService {
    List<String> calculateDir(String path);

    Map<String,String> toPinyins(List<String> hotWords);

    List<String> calculateFile(String path);
}
