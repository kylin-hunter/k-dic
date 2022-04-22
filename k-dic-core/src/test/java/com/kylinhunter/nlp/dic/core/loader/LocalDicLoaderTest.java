package com.kylinhunter.nlp.dic.core.loader;

import com.kylinhunter.nlp.dic.core.dic.Dic;
import com.kylinhunter.nlp.dic.core.dictionary.Dictionary;
import com.kylinhunter.nlp.dic.core.dictionary.bean.FindContext;
import com.kylinhunter.nlp.dic.core.dictionary.constant.MatchLevel;
import com.kylinhunter.nlp.dic.core.dictionary.group.DictionaryGroup;
import com.kylinhunter.nlp.dic.core.dictionary.group.DictionaryGroupType;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.WordNode;
import com.kylinhunter.nlp.dic.core.loader.constants.DicType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LocalDicLoaderTest {

    @Test
    public void test() {
        Dic dic = DicManager.get(DicType.SENSITIVE);
        DictionaryGroup dictionaryGroup = dic.getDictionaryGroup();
        Dictionary<WordNode> dictionary = dictionaryGroup.getDic(DictionaryGroupType.HIGH);

        Assertions.assertTrue(dictionary.contains("北京"));
        Assertions.assertFalse(dictionary.contains("石家庄"));
        FindContext<WordNode> findContext = new FindContext<>();

        findContext.findLevel = 3;
        dictionary.find("北京", findContext);
        Assertions.assertTrue(findContext.matchLevel == MatchLevel.HIGH.getCode());

        dictionary.find("北`京", findContext);
        Assertions.assertTrue(findContext.matchLevel == MatchLevel.MIDDLE.getCode());

        dictionary.find("北``1京", findContext);

        Assertions.assertTrue(findContext.matchLevel == MatchLevel.LOW.getCode());

        dictionary = dictionaryGroup.getDic(DictionaryGroupType.MIDDLE);
        Assertions.assertTrue(dictionary.contains("石家庄"));
        Assertions.assertFalse(dictionary.contains("乌鲁木齐"));

        dictionary = dictionaryGroup.getDic(DictionaryGroupType.LOW);
        Assertions.assertTrue(dictionary.contains("乌鲁木齐"));
        Assertions.assertFalse(dictionary.contains("北京"));

    }
}