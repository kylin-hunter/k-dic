package com.kylinhunter.nlp.dic.commons.io.file;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.kylinhunter.nlp.dic.commons.io.ResourceHelper;

class FileUtilTest {

    @Test
    void correctPath() {
        File distFile = UserDirUtils.getFile("/build/resources/test/test/test.txt", false);

        String path = "classpath:/test/test.txt";
        File pathNew = FileUtil.getFile(ResourceHelper.getPathInfo(path));
        Assertions.assertEquals(distFile.getAbsolutePath(), pathNew.getAbsolutePath());

        path = "classpath:test/test.txt";
        pathNew = FileUtil.getFile(ResourceHelper.getPathInfo(path));
        Assertions.assertEquals(distFile.getAbsolutePath(), pathNew.getAbsolutePath());

        path = "$user.dir$/test/test.txt";
        distFile = UserDirUtils.getFile("test/test.txt", false);
        pathNew = FileUtil.getFile(ResourceHelper.getPathInfo(path));
        Assertions.assertEquals(distFile.getAbsolutePath(), pathNew.getAbsolutePath());
    }

    @Test
    void process() {
        String path = "classpath:/test/test.txt";
        File distFile = FileUtil.getFile(ResourceHelper.getPathInfo(path));
        AtomicInteger lines = new AtomicInteger(0);
        FileUtil.process(distFile, "UTF-8", (line -> lines.incrementAndGet()));
        assertEquals(1, lines.get());
    }
}