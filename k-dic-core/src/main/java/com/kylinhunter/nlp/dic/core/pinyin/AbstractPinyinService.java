package com.kylinhunter.nlp.dic.core.pinyin;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2022/1/1
 **/
@Slf4j
public abstract class AbstractPinyinService implements PinyinService {
    protected HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();

    protected static String[] EMPTY = new String[0];

    /**
     * @param c
     * @return java.lang.String
     * @throws
     * @title toPinyin
     * @description
     * @author BiJi'an
     * @updateTime 2022/1/1 4:33 下午
     */
    public String toPinyin(char c) {
        String[] pinyin = toPinyins(c);
        if (pinyin != null && pinyin.length > 0) {
            return pinyin[0];
        } else {
            return null;
        }
    }

    /**
     * @param pinYinStr
     * @return java.lang.String
     * @throws
     * @title toPinyin
     * @description
     * @author BiJi'an
     * @updateTime 2022/1/1 4:34 下午
     */

    public String toPinyin(String pinYinStr) {

        StringBuilder sb = new StringBuilder();
        String tempStr = null;
        for (int i = 0; i < pinYinStr.length(); i++) {

            tempStr = this.toPinyin(pinYinStr.charAt(i));
            if (tempStr == null) {
                sb.append(pinYinStr.charAt(i));
            } else {
                sb.append(tempStr);
            }
        }

        return sb.toString();

    }

    /**
     * @param c
     * @return java.lang.String[]
     * @throws
     * @title toPinyins
     * @description
     * @author BiJi'an
     * @updateTime 2022/1/1 4:34 下午
     */
    public String[] toPinyins(char c) {

        try {
            String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
            if (pinyin != null && pinyin.length > 0) {
                return pinyin;
            } else {
                return EMPTY;
            }

        } catch (Exception e) {
            log.error("get pinyin error", e);
        }
        return EMPTY;

    }

    /**
     * @param oriStr
     * @param maxSize
     * @return java.lang.String[]
     * @title toPinyins
     * @description
     * @author BiJi'an
     * @updateTime 2022/1/1 4:36 下午
     * @throw
     */
    public String[] toPinyins(String oriStr, int maxSize) { // 优化拼音组件防止内存溢出

        List<String[]> allPinyins = Lists.newArrayList();

        allPinyins.add(new String[oriStr.length()]);
        String[] curCharPinyins = null;
        for (int curChar = 0; curChar < oriStr.length(); curChar++) {
            curCharPinyins = this.toPinyins(oriStr.charAt(curChar));

            if (curCharPinyins == null || curCharPinyins.length <= 0) {

                if (allPinyins.size() == 1) {
                    allPinyins.get(0)[curChar] = oriStr.substring(curChar, curChar + 1);
                } else {
                    String tmp = oriStr.substring(curChar, curChar + 1);
                    for (String[] charPinyin : allPinyins) {
                        charPinyin[curChar] = tmp;
                    }
                }

            } else if (curCharPinyins.length == 1) {

                if (allPinyins.size() == 1) {
                    allPinyins.get(0)[curChar] = curCharPinyins[0];
                } else {
                    for (String[] charPinyin : allPinyins) {
                        charPinyin[curChar] = curCharPinyins[0];
                    }
                }

            } else {

                if (allPinyins.size() == 1) {
                    for (int j = 0; j < curCharPinyins.length; j++) {

                        if (j == 0) {
                            allPinyins.get(0)[curChar] = curCharPinyins[0];
                        } else {
                            if (!curCharPinyins[j].equals(curCharPinyins[j - 1])) {
                                if (maxSize <= 0 || allPinyins.size() < maxSize) {

                                    String[] newCopy = Arrays.copyOf(allPinyins.get(0), allPinyins.get(0).length);
                                    newCopy[curChar] = curCharPinyins[j];
                                    allPinyins.add(newCopy);
                                }

                            }

                        }
                    }

                } else {

                    int curLen = allPinyins.size();

                    for (int j = 0; j < curCharPinyins.length; j++) {
                        if (j == 0) {
                            for (String[] charPinyin : allPinyins) {
                                charPinyin[curChar] = curCharPinyins[j];
                            }
                        } else {
                            if (!curCharPinyins[j].equals(curCharPinyins[j - 1])) {

                                for (int k = 0; k < curLen; k++) {
                                    if (maxSize <= 0 || allPinyins.size() < maxSize) {

                                        String[] newCopy = Arrays.copyOf(allPinyins.get(k), allPinyins.get(k).length);
                                        newCopy[curChar] = curCharPinyins[j];
                                        allPinyins.add(newCopy);

                                    }
                                }
                            }

                        }
                    }

                }
            }

        }

        if (allPinyins.size() == 1) {
            return new String[] {StringUtils.join(allPinyins.get(0))};
        } else {
            return allPinyins.stream().map(StringUtils::join
            ).toArray(String[]::new);
        }

    }

}