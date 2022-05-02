package com.kylinhunter.nlp.dic.commons.io.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.Charsets;

import com.kylinhunter.nlp.dic.commons.exception.internal.KIOException;
import com.kylinhunter.nlp.dic.commons.io.PathInfo;
import com.kylinhunter.nlp.dic.commons.io.PathType;
import com.kylinhunter.nlp.dic.commons.io.ResourceHelper;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public class FileUtil {

    /**
     * @param pathInfo pathInfo
     * @return java.lang.String
     * @title correctPath
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-21 00:52
     */
    public static File getFile(PathInfo pathInfo) {
        if (pathInfo != null) {
            PathType pathType = pathInfo.getPathType();
            if (pathType == PathType.CLASSPATH) {
                return ResourceHelper.getFileInClassPath(pathInfo.getPath());

            } else {
                return new File(pathInfo.getPath());
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
