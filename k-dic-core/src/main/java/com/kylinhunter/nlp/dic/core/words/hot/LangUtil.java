package com.kylinhunter.nlp.dic.core.words.hot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2022-03-28 16:19
 **/
public class LangUtil {
    private static final Pattern LETTER_DIGIT_CHINESE = Pattern.compile("^[,_a-z0-9A-Z\u4e00-\u9fa5]+$");
    private static final Pattern CHINESE = Pattern.compile("^[\u4e00-\u9fa5]+$");
    private static final Pattern LETTER_DIGIT = Pattern.compile("[,_a-z0-9A-Z]");

    public static boolean isAlpha(String text) {
        for (char c : text.toCharArray()) {
            if (!(((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param str
     * @return boolean
     * @throws
     * @title 中英文和数字
     * @description
     * @author BiJi'an
     * @updateTime 2022-04-21 20:24
     */
    public static boolean isLetterDigitOrChinese(String str) {
        if (str != null && str.length() > 0) {
            Matcher m = LETTER_DIGIT_CHINESE.matcher(str);
            return m.matches();
        }
        return false;
    }

    /**
     * @param str
     * @return boolean
     * @throws
     * @title isChinese
     * @description
     * @author BiJi'an
     * @updateTime 2022-04-25 15:05
     */
    public static boolean isChinese(String str) {
        if (str != null && str.length() > 0) {
            Matcher m = CHINESE.matcher(str);
            return m.matches();
        }
        return false;

    }
}
