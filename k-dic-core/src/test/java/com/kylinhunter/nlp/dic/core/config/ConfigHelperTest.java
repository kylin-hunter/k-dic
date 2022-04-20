package com.kylinhunter.nlp.dic.core.config;

import org.junit.jupiter.api.Test;

class ConfigHelperTest {


    @Test
    public void test(){
       Config config= ConfigHelper.load();
       System.out.println(config);
    }

}