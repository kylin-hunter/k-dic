package com.kylinhunter.nlp.dic.core.dic.local;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.kylinhunter.nlp.dic.commons.io.file.UserDirUtils;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.HitMode;
import com.kylinhunter.nlp.dic.core.dic.bean.DicData;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;

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
     * @updateTime 2022-01-01 22:58
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
                        dicData.setAssistWords("assistWords" + i);
                        dicData.setRelationWords("relationWords" + i);
                        dicData.setHitMode(HitMode.HIGH);
                        dicData.setType(1);
                        dicData.setClassId(1);
                        list.add(dicData);
                    }
                    return list;
                });

    }

}
