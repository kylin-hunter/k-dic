package com.kylinhunter.nlp.dic.commons.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;

import com.kylinhunter.nlp.dic.commons.exception.internal.KParamException;
import com.kylinhunter.nlp.dic.commons.io.file.UserDirUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
@Slf4j
public class ResourceHelper {
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
    public static PathInfo getPathInfo(String path) {
        if (!StringUtils.isEmpty(path)) {
            if (path.startsWith(CLASSPATH_TAG)) {
                return new PathInfo(PathType.CLASSPATH, path.replace(CLASSPATH_TAG, ""));
            } else {
                return new PathInfo(PathType.FILESYSTEM, path.replace(USER_DIR_TAG,
                        UserDirUtils.get().getAbsolutePath()));
            }
        }
        return null;
    }

    /**
     * @param pathInfo
     * @return java.io.InputStream
     * @throws
     * @title getInputStream
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-03 02:11
     */
    public static InputStream getInputStream(PathInfo pathInfo, String subPath) {

        if (pathInfo != null) {
            PathType pathType = pathInfo.getPathType();
            if (pathType == PathType.CLASSPATH) {
                return ResourceHelper.getInputStreamInClassPath(pathInfo.getPath() + "/" + subPath);

            } else {
                File file = new File(pathInfo.getPath(), subPath);
                try {
                    if (file.exists() && file.isFile()) {
                        return new FileInputStream(file);
                    }
                } catch (FileNotFoundException e) {
                    throw new KParamException("invalid file " + file.getAbsolutePath(), e);

                }
            }
        }
        return null;
    }

    /**
     * @param resourcePath the path
     * @return java.io.InputStream
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
     * @param resourcePath resourcePath
     * @return java.io.InputStreamReader
     * @title getStreamReaderInClassPath
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 02:11
     */
    public static InputStreamReader getStreamReaderInClassPath(String resourcePath) throws IOException {
        return getStreamReaderInClassPath(resourcePath, "UTF-8");
    }

    /**
     * @param resourcePath resourcePath
     * @param charset      charset
     * @return java.io.InputStreamReader
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
                in = ResourceHelper.class.getResourceAsStream(resourcePath);
                if (in != null) {
                    return new InputStreamReader(in, charset);

                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            throw new IOException("getStreamReaderInClassPath error", e);
        }

    }

    /**
     * @param resourcePath resourcePath
     * @return java.io.File
     * @title getFileInClassPath
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 02:12
     */

    public static File getFileInClassPath(String resourcePath) {

        URL url = ResourceHelper.class.getClassLoader().getResource(resourcePath);
        if (url == null) {
            url = ResourceHelper.class.getResource(resourcePath);
        }
        return getFile(url);
    }

    /**
     * @param url url
     * @return java.io.File
     * @title getFile
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 02:11
     */
    public static File getFile(URL url) {
        File file;
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
