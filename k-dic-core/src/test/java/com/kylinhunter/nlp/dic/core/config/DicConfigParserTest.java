package com.kylinhunter.nlp.dic.core.config;

import org.junit.jupiter.api.Test;

class DicConfigParserTest {


    @Test
    public void test(){
       Config config= DicConfigParser.load();
       System.out.println(config);
    }

}