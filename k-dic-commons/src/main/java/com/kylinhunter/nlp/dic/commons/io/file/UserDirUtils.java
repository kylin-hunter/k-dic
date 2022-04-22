package com.kylinhunter.nlp.dic.commons.io.file;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.kylinhunter.nlp.dic.commons.exception.internal.KIOException;
import com.kylinhunter.nlp.dic.commons.exception.internal.KInternalException;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022/1/1
 **/
public class UserDirUtils {
    private static final File USER_DIR = new File(System.getProperty("user.dir"));
    private static final String PATH_OF_USER_DIR = USER_DIR.getAbsolutePath();
    private static final String USER_DIR_CONFIG = "config";
    private static final String USER_DIR_TMP = "tmp";
    private static final String USER_DIR_TMP_JAVA = USER_DIR_TMP + "/src/main/java";
    private static final String USER_DIR_TMP_RESOURCE = USER_DIR_TMP + "/src/main/resources";

    /**
     * @return java.io.File
     * @throws
     * @title getUserDir
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 01:57
     */
    public static File get() {
        return USER_DIR;
    }

    public static File getDir(String child) {
        return getDir(child, true);
    }

    /**
     * @param child
     * @return java.io.File
     * @throws
     * @title getUserDir
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 01:57
     */
    public static File getDir(String child, boolean create) {
        File file = new File(USER_DIR, child);
        if (file.exists()) {
            if (file.isFile()) {
                throw new KIOException("not a dir" + file.getAbsolutePath());
            }
        } else {
            if (create) {
                forceMkdir(file);
            }
        }
        return file;
    }

    public static File getFile(String child) {
        return getFile(child, true);
    }

    /**
     * @param child
     * @return java.io.File
     * @throws
     * @title getUserDir
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 01:57
     */
    public static File getFile(String child, boolean createParent) {
        File file = new File(USER_DIR, child);
        if (file.exists()) {
            if (file.isDirectory()) {
                throw new KIOException("not a file" + file.getAbsolutePath());
            }
        } else {
            if (!file.getParentFile().exists() && createParent) {
                forceMkdir(file.getParentFile());
            }
        }
        return file;
    }

    /**
     * @return java.io.File
     * @throws
     * @title getUserDirTmp
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 01:57
     */
    public static File getTmpDir() {
        return getDir(USER_DIR_TMP, true);
    }

    public static File getTmpDir(String child) {
        return getDir(USER_DIR_TMP + File.separator + child, true);
    }

    /**
     * @param child
     * @param create
     * @return java.io.File
     * @throws
     * @title getUserDirTmp
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 01:57
     */
    public static File getTmpDir(String child, boolean create) {
        return getDir(USER_DIR_TMP + File.separator + child, create);

    }

    public static File getTmpFile(String child) {
        return getFile(USER_DIR_TMP + File.separator +  child, true);
    }

    /**
     * @param child
     * @return java.io.File
     * @throws
     * @title getUserDir
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 01:57
     */
    public static File getTmpFile(String child, boolean createParent) {
        return getFile(USER_DIR_TMP + File.separator +  child, createParent);

    }

    /**
     * @return java.io.File
     * @throws
     * @title getExClassPath
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 01:58
     */
    public static File getDirConfig() {
        return getDir(USER_DIR_CONFIG, true);

    }

    /**
     * @return java.io.File
     * @throws
     * @title getUserDirTmpJava
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 01:57
     */
    public static File getTmpDirJava() {
        return getDir(USER_DIR_TMP_JAVA, true);
    }

    /**
     * @return java.io.File
     * @throws
     * @title getUserDirTmpResource
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 01:58
     */
    public static File getTmpDirResource() {
        return getDir(USER_DIR_TMP_RESOURCE, true);
    }

    /**
     * @param directory
     * @return void
     * @throws
     * @title forceMkdir
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 01:58
     */
    public static void forceMkdir(final File directory) {
        try {
            FileUtils.forceMkdir(directory);
        } catch (IOException e) {
            throw new KInternalException("forceMkdir  error", e);
        }
    }

    /**
     * @param file
     * @return boolean
     * @throws
     * @title deleteQuietly
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 01:58
     */
    public static boolean deleteQuietly(final File file) {

        if (file != null && file.getAbsolutePath().indexOf(PATH_OF_USER_DIR) >= 0) {
            return FileUtils.deleteQuietly(file);
        }
        return false;

    }

}
