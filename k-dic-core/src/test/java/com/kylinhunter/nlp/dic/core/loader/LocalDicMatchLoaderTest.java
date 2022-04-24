package com.kylinhunter.nlp.dic.core.loader;

import com.kylinhunter.nlp.dic.core.dictionary.Dictionary;
import com.kylinhunter.nlp.dic.core.dictionary.group.DictionaryGroup;
import com.kylinhunter.nlp.dic.core.dictionary.group.GroupType;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.WordNode;
import com.kylinhunter.nlp.dic.core.loader.constants.DicType;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LocalDicMatchLoaderTest {

    @Test
    public void test() {
        Dic dic = DicManager.get(DicType.SENSITIVE);
        DictionaryGroup dictionaryGroup = dic.getDicMatch().getDictionaryGroup();
        Dictionary<WordNode> dictionary = dictionaryGroup.get(GroupType.HIGH);

        Assertions.assertTrue(dictionary.contains("北京"));
        Assertions.assertFalse(dictionary.contains("石家庄"));

    }
}