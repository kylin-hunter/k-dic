package io.github.kylinhunter.tools.dic.core.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConfigHelperTest {


    @Test
    public void test() {
        Config config = ConfigHelper.get();
        Assertions.assertTrue(config.getLoad().getLocal().isAutoScan());
        Assertions.assertTrue(config.getDics().size() > 0);
        System.out.println(config);
    }

}