package com.kylinhunter.nlp.dic.core.loader;

import com.kylinhunter.nlp.dic.core.dic.Dic;
import com.kylinhunter.nlp.dic.core.dictionary.Dictionary;
import com.kylinhunter.nlp.dic.core.dictionary.bean.MatchContext;
import com.kylinhunter.nlp.dic.core.dictionary.constant.MatchLevel;
import com.kylinhunter.nlp.dic.core.dictionary.group.DictionaryGroup;
import com.kylinhunter.nlp.dic.core.dictionary.group.GroupType;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.WordNode;
import com.kylinhunter.nlp.dic.core.loader.constants.DicType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LocalDicLoaderTest {

    @Test
    public void test() {
        Dic dic = DicManager.get(DicType.SENSITIVE);
        DictionaryGroup dictionaryGroup = dic.getDictionaryGroup();
        Dictionary<WordNode> dictionary = dictionaryGroup.get(GroupType.HIGH);

        Assertions.assertTrue(dictionary.contains("北京"));
        Assertions.assertFalse(dictionary.contains("石家庄"));
        MatchContext<WordNode> matchContext = new MatchContext<>();

        matchContext.findLevel = 3;
        dictionary.match("北京", matchContext);
        Assertions.assertTrue(matchContext.matchLevel == MatchLevel.HIGH.getCode());

        dictionary.match("北`京", matchContext);
        Assertions.assertTrue(matchContext.matchLevel == MatchLevel.MIDDLE.getCode());

        dictionary.match("北``1京", matchContext);

        Assertions.assertTrue(matchContext.matchLevel == MatchLevel.LOW.getCode());

        dictionary = dictionaryGroup.get(GroupType.MIDDLE);
        Assertions.assertTrue(dictionary.contains("石家庄"));
        Assertions.assertFalse(dictionary.contains("乌鲁木齐"));

        dictionary = dictionaryGroup.get(GroupType.LOW);
        Assertions.assertTrue(dictionary.contains("乌鲁木齐"));
        Assertions.assertFalse(dictionary.contains("北京"));

    }
}