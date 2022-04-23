
package com.kylinhunter.nlp.dic.core.dic.component;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import com.kylinhunter.nlp.dic.core.dictionary.constant.DictionaryConst;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.kylinhunter.nlp.dic.commons.io.file.FileUtil;
import com.kylinhunter.nlp.dic.commons.io.ResourceHelper;
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01
 **/
@Slf4j
public class DicSkipper {
    private static DicSkipper singleton = new DicSkipper();
    public static final char SPECIAL_CHAR = DictionaryConst.SPECIAL_CHAR;

    private DicSkipper() {
        init();
    }

    public static DicSkipper getInstance() {
        return singleton;
    }

    private Set<Character> hiChars = new HashSet<>();
    private Set<Character> defaultChars = new HashSet<>();

    private static final String CONFIG_PATH = "/config/dic/dic_skipper.txt";

    public void init() {

        InputStream in = null;
        try {
            in = ResourceHelper.getInputStreamInClassPath(CONFIG_PATH);

            FileUtil.process(in, "UTF-8", (line) -> {
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
     * @return void
     * @throws
     * @title processForFindLevelHigh
     * @description
     * @author BiJi'an
     * @updateTime 2022-04-15 10:59
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
     * determine whether a character is a punctuation
     *
     * @param c
     * @return
     */
    public boolean isSkip(FindLevel findLevel, char c) {
        if (findLevel == FindLevel.HIGH) {
            return hiChars.contains(c);
        } else {
            return defaultChars.contains(c);
        }
    }

    /**
     * @param c
     * @return boolean
     * @throws
     * @title isSkip
     * @description
     * @author BiJi'an
     * @updateTime 2022/3/26 8:38 下午
     */
    public boolean isSkip(char c) {
        return defaultChars.contains(c);
    }

    /**
     * @param findLevel
     * @param c
     * @return boolean
     * @throws
     * @title remove
     * @description
     * @author BiJi'an
     * @updateTime 2022-04-15 11:00
     */
    public boolean remove(FindLevel findLevel, char c) {
        if (findLevel == FindLevel.HIGH) {
            return hiChars.remove(c);
        } else {
            return defaultChars.remove(c);

        }
    }

}