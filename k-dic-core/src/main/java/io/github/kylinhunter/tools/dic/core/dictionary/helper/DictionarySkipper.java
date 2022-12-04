
package io.github.kylinhunter.tools.dic.core.dictionary.helper;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Sets;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.exception.embed.InitException;
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
@C
public class DictionarySkipper {

    @SuppressWarnings("unchecked")
    private final Set<Character>[] skipCharsArr = new HashSet[4];
    private static final String CONFIG_PATH = "/k-dic-default/dic_skipper.txt";

    public DictionarySkipper() {
        init();
    }

    public void init() {

        Set<Character> skipChars = Sets.newHashSet();
        try (InputStream in = ResourceHelper.getInputStreamInClassPath(CONFIG_PATH)) {
            FileReaderUtils.process(in, "UTF-8", (line) -> {
                if (!StringUtils.isEmpty(line)) {
                    line = line.trim();
                    if (line.length() == 1) {
                        skipChars.add(line.charAt(0));

                        // log.info("add:" + line.charAt(0));
                    } else {
                        throw new InitException("a line can only  have on charactor,the line :" + line);
                    }
                }
            });

            processDefaultSkipChars(skipChars);
            for (FindLevel findLevel : FindLevel.values()) {
                Set<Character> chars = new HashSet<>(2048); // lagger size for reducing confict
                chars.addAll(skipChars);
                skipCharsArr[findLevel.getCode()] = chars;
            }

            log.info("init ok");
        } catch (Exception e) {
            throw new InitException("init error", e);
        }

    }

    /**
     * @title processForFindLevelHigh
     * @description
     * @author BiJi'an
     * @date 2022-01-15 10:59
     */
    private void processDefaultSkipChars(Set<Character> skipChars) {
        skipChars.add(' ');
        skipChars.add('　');
        skipChars.add('\t');
        skipChars.add('\n');
        skipChars.add('\r');

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
        return skipCharsArr[findLevel.code].contains(c);
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
        return skipCharsArr[findLevel.code].remove(c);

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
            char c = words.charAt(i);
            Set<Character> skipChars = skipCharsArr[findLevel.code];
            if (skipChars.contains(c)) {
                skipChars.remove(c);
                log.error("remove {} char={}", findLevel, c);
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
        Set<Character> skipChars = skipCharsArr[findLevel.code];
        for (int i = 0; i < textChars.length; i++) {
            if (skipChars.contains(textChars[i])) {
                textChars[i] = DicConst.SKIP_NULL;
            }
        }
        return textChars;
    }

}