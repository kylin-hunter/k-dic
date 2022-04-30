package com.kylinhunter.nlp.dic.core.dic;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.kylinhunter.nlp.dic.core.dic.constants.DicType;
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;
import com.kylinhunter.nlp.dic.core.match.TestDicMatchHelper;
import com.kylinhunter.nlp.dic.core.match.bean.MatchResult;

class LocalDicMatchLoaderTest {

    @Test
    public void testSensitive() {
        Dic dic = DicManager.get(DicType.SENSITIVE);
        String text = "听说苍井空和苍**井空和苍井**1**空"
                + "还有波多野结衣和波多野结**衣和波多野结**1**衣在AV中都是好同志，"
                + "和胸部和胸*&部和胸**2&&部"
                + "和大腿和大&&腿和大&&1*&腿都不能露出来，"
                + "日本的内衣和内&&衣和内&(1&衣"
                + "都很巨大和巨**大和巨&&啊&&大的";
        FindLevel findLevel = FindLevel.HIGH;
        List<MatchResult> matchResults = dic.match(text, findLevel);
        List<String> printResult = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(new String[] {
                        "1:2:5:苍井空:苍井空:null",
                        "1:22:27:波多野结衣:波多野结衣:[AV]",
                        "1:57:59:胸部:胸部:null",
                        "1:73:75:大腿:大腿:[日本]",
                        "1:98:100:内衣:内衣:null",
                        "1:114:116:巨大:巨大:[日本, AV]"
                },
                printResult.toArray());

        findLevel = FindLevel.HIGH_MIDDLE;
        matchResults = dic.match(text, findLevel);
        printResult = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(new String[] {
                        "1:2:5:苍井空:苍井空:null",
                        "1:22:27:波多野结衣:波多野结衣:[AV]",
                        "1:57:59:胸部:胸部:null",
                        "2:60:64:胸*&部:胸部:null",
                        "1:73:75:大腿:大腿:[日本]",
                        "2:76:80:大&&腿:大腿:[日本]",
                        "1:98:100:内衣:内衣:null",
                        "2:101:105:内&&衣:内衣:null",
                        "1:114:116:巨大:巨大:[日本, AV]",
                        "2:117:121:巨**大:巨大:[日本, AV]"
                },
                printResult.toArray());

        findLevel = FindLevel.HIGH_MIDDLE_LOW;
        matchResults = dic.match(text, findLevel);
        printResult = TestDicMatchHelper.printResult(text, findLevel, matchResults);
        Assertions.assertArrayEquals(new String[] {
                        "1:2:5:苍井空:苍井空:null",
                        "1:22:27:波多野结衣:波多野结衣:[AV]",
                        "1:57:59:胸部:胸部:null",
                        "2:60:64:胸*&部:胸部:null",
                        "1:73:75:大腿:大腿:[日本]",
                        "2:76:80:大&&腿:大腿:[日本]",
                        "1:98:100:内衣:内衣:null",
                        "2:101:105:内&&衣:内衣:null",
                        "3:106:112:内&(1&衣:内衣:null",
                        "1:114:116:巨大:巨大:[日本, AV]",
                        "2:117:121:巨**大:巨大:[日本, AV]",
                        "3:122:129:巨&&啊&&大:巨大:[日本, AV]"
                },
                printResult.toArray());
    }
}