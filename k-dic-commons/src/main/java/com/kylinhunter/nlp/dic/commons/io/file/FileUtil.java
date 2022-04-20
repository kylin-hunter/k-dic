package com.kylinhunter.nlp.dic.commons.io.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.Charsets;

import com.kylinhunter.nlp.dic.commons.exception.internal.KIOException;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2022/1/1
 **/
public class FileUtil {

    /**
     * @param file
     * @param encoding
     * @param processor
     * @return void
     * @throws
     * @title process
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 01:48
     */
    public static void process(File file, String encoding, LinesProcessor<?> processor) {

        try (InputStream input = new FileInputStream(file)) {
            process(input, encoding, processor);
        } catch (Exception e) {
            throw new KIOException("process error", e);
        }

    }

    /**
     * @param input
     * @param encoding
     * @param processor
     * @return void
     * @throws
     * @title process
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 02:00
     */
    public static void process(InputStream input, String encoding, LinesProcessor<?> processor) {

        try (InputStreamReader streamReader = new InputStreamReader(input, Charsets.toCharset(encoding));
             BufferedReader bufferReader = new BufferedReader(streamReader);) {
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
