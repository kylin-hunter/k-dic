package io.github.kylinhunter.tools.dic.core.dictionary.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;

class DictionarySkipperTest {

    @Test
    public void test() {
        DictionarySkipper dictionarySkipper = CF.get(DictionarySkipper.class);
        Assertions.assertFalse(dictionarySkipper.isSkip(FindLevel.HIGH, '1'));
        Assertions.assertTrue(dictionarySkipper.isSkip(FindLevel.HIGH, '<'));
        Assertions.assertTrue(dictionarySkipper.isSkip(FindLevel.HIGH, '='));
        Assertions.assertTrue(dictionarySkipper.isSkip(FindLevel.HIGH, '['));

    }

}