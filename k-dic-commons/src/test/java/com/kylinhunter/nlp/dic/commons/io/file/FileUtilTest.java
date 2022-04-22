package com.kylinhunter.nlp.dic.commons.io.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilTest {

    @Test
    void correctPath() {
        File distFile = UserDirUtils.getFile("/build/resources/test/test/test.txt", false);

        String path = "classpath:/test/test.txt";
        File pathNew = FileUtil.correctPath(path);
        Assertions.assertEquals(distFile.getAbsolutePath(), pathNew.getAbsolutePath());

        path = "classpath:test/test.txt";
        pathNew = FileUtil.correctPath(path);
        Assertions.assertEquals(distFile.getAbsolutePath(), pathNew.getAbsolutePath());

        path = "$user.dir$/test/test.txt";
        distFile = UserDirUtils.getFile("test/test.txt", false);
        pathNew = FileUtil.correctPath(path);
        Assertions.assertEquals(distFile.getAbsolutePath(), pathNew.getAbsolutePath());
    }

    @Test
    void process() {
        String path = "classpath:/test/test.txt";
        File distFile = FileUtil.correctPath(path);
        AtomicInteger lines = new AtomicInteger(0);
        FileUtil.process(distFile, "UTF-8", (line -> {
            lines.incrementAndGet();
        }));
        assertEquals(1, lines.get());
    }
}