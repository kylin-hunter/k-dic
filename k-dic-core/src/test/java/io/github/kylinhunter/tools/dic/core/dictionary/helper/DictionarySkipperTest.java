package io.github.kylinhunter.tools.dic.core.dictionary.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.tools.dic.core.dictionary.helper.DictionarySkipper;

class DictionarySkipperTest {

    @Test
    public void test() {

        Assertions.assertFalse(DictionarySkipper.getInstance().isSkip('1'));
        Assertions.assertTrue(DictionarySkipper.getInstance().isSkip('<'));
        Assertions.assertTrue(DictionarySkipper.getInstance().isSkip('='));
        Assertions.assertTrue(DictionarySkipper.getInstance().isSkip('['));

    }

}