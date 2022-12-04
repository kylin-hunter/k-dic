package io.github.kylinhunter.tools.dic.app.local;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;

import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import io.github.kylinhunter.commons.io.file.reader.FileReaderUtils;
import io.github.kylinhunter.tools.dic.app.bean.DicData;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.HitMode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author bijian
 * @version 1.0
 * @description TODO
 * @date 2022/1/22 1:05
 */
@Slf4j
public class LocalFileHelper {

    /**
     * @title writeDemo
     * @description
     * @author BiJi'an
     * @date 2022-01-01 22:58
     */
    public static void writeDemo() {
        File file = UserDirUtils.getTmpFile("dic_test.xlsx");
        log.info("write to {}", file.getAbsolutePath());
        EasyExcel.write(file, DicData.class)
                .sheet("Config")
                .doWrite(() -> {
                    List<DicData> list = ListUtils.newArrayList();
                    for (int i = 0; i < 10; i++) {
                        DicData dicData = new DicData();
                        dicData.setWords("words" + i);
                        dicData.setAssistWords("assistedKeywords" + i);
                        dicData.setRelationWords("targetWords" + i);
                        dicData.setHitMode(HitMode.HIGH);
                        dicData.setType(1);
                        dicData.setClassId(1);
                        list.add(dicData);
                    }
                    return list;
                });

    }

    public static void test() {
        InputStream in = ResourceHelper.getInputStreamInClassPath("dict/main2012.dic");
        AtomicInteger num1 = new AtomicInteger(0);
        AtomicInteger num2 = new AtomicInteger(0);

        FileReaderUtils.process(in, "UTF-8", (e) -> {
            //            System.out.println(e);
            num1.incrementAndGet();
        });

        in = ResourceHelper.getInputStreamInClassPath("dict/main_dic_2020.dic");
        FileReaderUtils.process(in, "UTF-8", (e) -> {
            //            System.out.println(e);
            num2.incrementAndGet();
        });
        //        System.out.println(num1 + ":" + num2);

    }

    public static void main(String[] args) {
        LocalFileHelper.test();
    }

}
