package io.github.kylinhunter.tools.dic.core.dictionary;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.tools.dic.core.dictionary.constant.DicConst;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.MatchLevel;
import io.github.kylinhunter.tools.dic.core.trie.TrieStatHelper;

public class DictionaryTest {

    private static final char SC = DicConst.SKIP_NULL;
    static Dictionary<Long> dictionary = new Dictionary<>();

    @BeforeAll
    static void init() {
        // 定义了三种匹配模式
        // 1、精确匹配，一模一样
        // 2、中度匹配、词中含有常见符号，
        // 3、低度匹配、含有常见符号之外，额外含有若干个其他字符，例如一个中英文字符

        dictionary.put("毕庄");
        dictionary.put("毕家庄");
        dictionary.put("毕继安");
        dictionary.put("毕平安");
        dictionary.put("海淀");
        dictionary.put("海淀黄庄");
        dictionary.put("中华人民共和国");
        dictionary.put("中华");
        dictionary.put("中华民族");
        TrieStatHelper.showStatistics(dictionary);
        TrieStatHelper.showConfilicMessage(dictionary);
        TrieStatHelper.show(dictionary);
    }

    @Test
    public void test1() {

        Dictionary.MatchContext<Long> matchContext = new Dictionary.MatchContext<>();
        matchContext.findLevel = FindLevel.HIGH.getCode();

        dictionary.match("毕", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        dictionary.match("毕", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        dictionary.match("毕庄", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.HIGH.getCode());

        dictionary.match("毕庄1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        dictionary.match("毕继安", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.HIGH.getCode());

        dictionary.match("毕继安1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        dictionary.match("海淀黄庄", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.HIGH.getCode());

        dictionary.match("海淀黄庄1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

    }

    @Test
    public void test2() {

        Dictionary.MatchContext<Long> matchContext = new Dictionary.MatchContext<>();
        matchContext.findLevel = FindLevel.HIGH_MIDDLE.getCode();

        dictionary.match("海淀黄庄", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.HIGH.getCode());
        dictionary.match("海淀黄庄1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        dictionary.match("海淀" + SC + SC + "黄" + SC + SC + "庄", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.MIDDLE.getCode());

        dictionary.match("海淀" + SC + SC + "黄" + SC + SC + "庄1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        dictionary.setSkipMaxLen(2);
        dictionary.match("海淀" + SC + SC + SC + SC + "黄" + SC + SC + SC + SC + "庄", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        dictionary.match("海淀" + SC + SC + SC + SC + "黄" + SC + SC + SC + SC + "庄1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        dictionary.setSkipMaxLen(4);
        dictionary.match("海淀" + SC + SC + SC + SC + "黄" + SC + SC + SC + SC + "庄", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.MIDDLE.getCode());

        dictionary.match("海淀" + SC + SC + SC + SC + "黄" + SC + SC + SC + SC + "庄1", matchContext);
        Assertions.assertEquals(MatchLevel.NONE.getCode(), matchContext.matchLevel);

    }

    @Test
    public void test3() {

        Dictionary.MatchContext<Long> matchContext = new Dictionary.MatchContext<>();

        matchContext.findLevel = FindLevel.HIGH_MIDDLE_LOW.getCode();
        dictionary.match("海淀黄庄", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.HIGH.getCode());
        dictionary.match("海淀黄庄1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);
        dictionary.match("海淀" + SC + SC + "黄" + SC + SC + "庄", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.MIDDLE.getCode());
        dictionary.match("海淀" + SC + SC + "黄" + SC + SC + "庄1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);
        dictionary.match("海淀" + SC + SC + "黄" + SC + SC + "1" + SC + SC + "庄", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.LOW.getCode());
        dictionary.match("海淀" + SC + SC + "黄" + SC + SC + "1" + SC + SC + "庄1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        dictionary.setSkipMaxLen(2);
        dictionary.match("海淀" + SC + SC + SC + SC + "黄" + SC + SC + SC + SC + "1庄", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        dictionary.match("海淀" + SC + SC + SC + SC + "黄" + SC + SC + SC + SC + "1庄1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

        dictionary.setSkipMaxLen(4);
        dictionary.match("海淀" + SC + SC + SC + SC + "黄" + SC + SC + SC + SC + "1庄", matchContext);
        Assertions.assertEquals(matchContext.matchLevel, MatchLevel.LOW.getCode());

        dictionary.match("海淀" + SC + SC + SC + SC + "黄" + SC + SC + SC + SC + "1庄1", matchContext);
        Assertions.assertEquals(0, matchContext.matchLevel);

    }
}
