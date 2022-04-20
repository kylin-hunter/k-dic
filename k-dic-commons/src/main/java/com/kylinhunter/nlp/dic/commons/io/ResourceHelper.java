package com.kylinhunter.nlp.dic.commons.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2022/1/1
 **/
@Slf4j
public class ResourceHelper {

    /**
     * @param resourcePath
     * @return java.io.InputStream
     * @throws
     * @title getInputStreamInClassPath
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 02:10
     */
    public static InputStream getInputStreamInClassPath(String resourcePath) {
        InputStream in = ResourceHelper.class.getClassLoader().getResourceAsStream(resourcePath);
        if (in != null) {
            return in;
        } else {
            return ResourceHelper.class.getResourceAsStream(resourcePath);
        }

    }

    /**
     * @param resourcePath
     * @return java.io.InputStreamReader
     * @throws
     * @title getStreamReaderInClassPath
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 02:11
     */
    public static InputStreamReader getStreamReaderInClassPath(String resourcePath) throws IOException {
        return getStreamReaderInClassPath(resourcePath, "UTF-8");
    }

    /**
     * @param resourcePath
     * @param charset
     * @return java.io.InputStreamReader
     * @throws
     * @title getStreamReaderInClassPath
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 02:11
     */
    public static InputStreamReader getStreamReaderInClassPath(String resourcePath, String charset) throws IOException {
        try {
            InputStream in = ResourceHelper.class.getClassLoader().getResourceAsStream(resourcePath);
            if (in != null) {
                return new InputStreamReader(in, charset);
            } else {
                return new InputStreamReader(ResourceHelper.class.getResourceAsStream(resourcePath), charset);
            }
        } catch (Exception e) {
            throw new IOException("getStreamReaderInClassPath error", e);
        }

    }

    /**
     * @param resourcePath
     * @return java.io.File
     * @throws
     * @title getFileInClassPath
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 02:12
     */

    public static File getFileInClassPath(String resourcePath) {

        URL url = ResourceHelper.class.getClassLoader().getResource(resourcePath);
        if (url != null) {
            return getFile(url);
        } else {
            url = ResourceHelper.class.getResource(resourcePath);
            return getFile(url);
        }
    }

    /**
     * @param url
     * @return java.io.File
     * @throws
     * @title getFile
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 02:11
     */
    public static File getFile(URL url) {
        File file = null;
        try {
            if (url != null) {
                file = new File(url.getPath());
                if (file.exists()) {
                    return file;
                } else {
                    file = new File(url.toURI().getPath());
                    if (file.exists()) {
                        return file;
                    }
                }
            }
        } catch (Exception e) {
            log.warn("get File error " + url, e);
        }
        return null;
    }
}
