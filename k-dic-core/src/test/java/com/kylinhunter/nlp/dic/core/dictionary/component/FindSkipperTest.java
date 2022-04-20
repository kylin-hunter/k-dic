package com.kylinhunter.nlp.dic.core.dictionary.component;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FindSkipperTest {

    @Test
    public void test() {

        Assertions.assertFalse(FindSkipper.getInstance().isSkip('1'));
        Assertions.assertTrue(FindSkipper.getInstance().isSkip('<'));
        Assertions.assertTrue(FindSkipper.getInstance().isSkip('='));
        Assertions.assertTrue(FindSkipper.getInstance().isSkip('['));

    }

}