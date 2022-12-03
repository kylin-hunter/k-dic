
package io.github.kylinhunter.tools.dic.core.dictionary.helper;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.io.file.reader.FileReaderUtils;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.DicConst;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01
 **/
@Slf4j
public class DictionarySkipper {
    private static final DictionarySkipper singleton = new DictionarySkipper();
    public static final char SPECIAL_CHAR = DicConst.SPECIAL_CHAR;

    private DictionarySkipper() {
        init();
    }

    public static DictionarySkipper getInstance() {
        return singleton;
    }

    private final Set<Character> hiChars = new HashSet<>();
    private final Set<Character> defaultChars = new HashSet<>();

    private static final String CONFIG_PATH = "/k-dic-default/dic_skipper.txt";

    public void init() {

        InputStream in = null;
        try {
            in = ResourceHelper.getInputStreamInClassPath(CONFIG_PATH);

            FileReaderUtils.process(in, "UTF-8", (line) -> {
                if (!StringUtils.isEmpty(line)) {
                    line = line.trim();
                    if (line.length() == 1) {
                        defaultChars.add(line.charAt(0));
                        //                        log.info("add:" + line.charAt(0));
                    } else {
                        log.error("error char:" + line);
                    }
                }
            });

            processForFindLevelHigh();

            log.info("init ok");

        } catch (Exception e) {
            throw new RuntimeException("init error", e);

        } finally {
            IOUtils.closeQuietly(in);
        }

    }

    /**
     * @title processForFindLevelHigh
     * @description
     * @author BiJi'an
     * @date 2022-01-15 10:59
     */
    private void processForFindLevelHigh() {
        hiChars.addAll(defaultChars);
        hiChars.add(' ');
        hiChars.add('　');
        hiChars.add('\t');
        hiChars.add('\n');
        hiChars.add('\r');
    }

    /**
     * @param findLevel findLevel
     * @param c         c
     * @return boolean
     * @title isSkip
     * @description
     * @author BiJi'an
     * @date 2022-12-04 02:50
     */
    public boolean isSkip(FindLevel findLevel, char c) {
        if (findLevel == FindLevel.HIGH) {
            return hiChars.contains(c);
        } else {
            return defaultChars.contains(c);
        }
    }

    /**
     * @param c c
     * @return boolean
     * @description isSkip
     * @date 2022/1/24 3:27
     * @author BiJi'an
     */
    public boolean isSkip(char c) {
        return defaultChars.contains(c);
    }

    /**
     * @param findLevel findLevel
     * @param c         c
     * @return boolean
     * @title remove
     * @description
     * @author BiJi'an
     * @date 2022-01-15 11:00
     */
    public boolean remove(FindLevel findLevel, char c) {
        if (findLevel == FindLevel.HIGH) {
            return hiChars.remove(c);
        } else {
            return defaultChars.remove(c);

        }
    }

    /**
     * @param findLevel findLevel
     * @param words     words
     * @return boolean
     * @title remove
     * @description
     * @author BiJi'an
     * @date 2022-01-15 11:00
     */
    public void remove(FindLevel findLevel, String words) {
        for (int i = 0; i < words.length(); i++) {
            if (words.charAt(i) != ' ' && !CharUtils.isAsciiAlphanumeric(words.charAt(i))) {
                if (this.remove(findLevel, words.charAt(i))) {
                    log.error("remove FindLevel.HIGH char skip:" + words.charAt(i));
                }
            }
        }

    }

    /**
     * @param text      text
     * @param findLevel findLevel
     * @return char[]
     * @title replaceSkipChar
     * @description
     * @author BiJi'an
     * @date 2022-12-04 02:51
     */
    public char[] replaceSkipChar(String text, FindLevel findLevel) {
        char[] textChars = text.toCharArray();
        for (int i = 0; i < textChars.length; i++) {
            if (this.isSkip(findLevel, textChars[i])) {
                textChars[i] = DictionarySkipper.SPECIAL_CHAR;
            }
        }
        return textChars;
    }

}