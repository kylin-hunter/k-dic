package com.kylinhunter.nlp.dic.commons.io.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.StringUtils;

import com.kylinhunter.nlp.dic.commons.exception.internal.KIOException;
import com.kylinhunter.nlp.dic.commons.io.ResourceHelper;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public class FileUtil {
    public static final String USER_DIR_TAG = "$user.dir$";
    public static final String CLASSPATH_TAG = "classpath:";

    /**
     * @param path path
     * @return java.lang.String
     * @title correctPath
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-21 00:52
     */
    public static File correctPath(String path) {
        if (!StringUtils.isEmpty(path)) {
            if (path.startsWith(CLASSPATH_TAG)) {
                path = path.replace(CLASSPATH_TAG, "");
                File file = ResourceHelper.getFileInClassPath(path);
                if (file != null) {
                    return file;
                }
            } else {
                return new File(path.replace(USER_DIR_TAG, UserDirUtils.get().getAbsolutePath()));
            }
        }
        return null;
    }

    /**
     * @param file      a file
     * @param encoding  the encoding of the file
     * @param processor a processor to process the file
     * @title process
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 01:48
     */
    public static void process(File file, String encoding, LinesProcessor processor) {

        try (InputStream input = new FileInputStream(file)) {
            process(input, encoding, processor);
        } catch (Exception e) {
            throw new KIOException("process error", e);
        }

    }

    /**
     * @param input     the input stream
     * @param encoding  the encoding
     * @param processor the processor
     * @title process
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 02:00
     */
    public static void process(InputStream input, String encoding, LinesProcessor processor) {

        try (InputStreamReader streamReader = new InputStreamReader(input, Charsets.toCharset(encoding));
             BufferedReader bufferReader = new BufferedReader(streamReader)) {
            String line = bufferReader.readLine();
            while (line != null) {
                processor.process(line);
                line = bufferReader.readLine();

            }
        } catch (Exception e) {
            throw new KIOException("process error", e);
        }
    }

}
