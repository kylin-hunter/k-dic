package com.kylinhunter.nlp.dic.core.dic.component;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DicMatchSkipperTest {

    @Test
    public void test() {

        Assertions.assertFalse(DicSkipper.getInstance().isSkip('1'));
        Assertions.assertTrue(DicSkipper.getInstance().isSkip('<'));
        Assertions.assertTrue(DicSkipper.getInstance().isSkip('='));
        Assertions.assertTrue(DicSkipper.getInstance().isSkip('['));

    }

}