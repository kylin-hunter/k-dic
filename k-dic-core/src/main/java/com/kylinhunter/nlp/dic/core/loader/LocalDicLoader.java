package com.kylinhunter.nlp.dic.core.loader;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.util.ListUtils;
import com.kylinhunter.nlp.dic.commons.io.ResourceHelper;
import com.kylinhunter.nlp.dic.commons.io.file.UserDirUtils;
import com.kylinhunter.nlp.dic.core.config.DicDataSourceLocal;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.HitMode;
import com.kylinhunter.nlp.dic.core.loader.bean.DicData;
import com.kylinhunter.nlp.dic.core.loader.common.AbstractDicLoader;
import com.kylinhunter.nlp.dic.core.config.Config;
import com.kylinhunter.nlp.dic.core.config.DicConfig;
import com.kylinhunter.nlp.dic.core.config.DicConfigParser;
import com.kylinhunter.nlp.dic.core.loader.constants.DicType;
import com.kylinhunter.nlp.dic.core.loader.monitor.LocalDicFileMonitor;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2022-01-01
 **/
@Slf4j
@Getter
@Setter
public class LocalDicLoader extends AbstractDicLoader {
    private static LocalDicLoader singleton = new LocalDicLoader();
    protected Config config = DicConfigParser.load();

    private LocalDicFileMonitor localDicFileMonitor = new LocalDicFileMonitor(config);

    private LocalDicLoader() {
        super();
        localDicFileMonitor.start();
    }

    public static LocalDicLoader getInstance() {
        return singleton;
    }

    /**
     * @param dicType
     * @return java.util.List<com.kylinhunter.nlp.Config.core.loader.bean.DicData>
     * @throws
     * @title load
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 22:58
     */
    protected List<DicData> loadDicData(DicType dicType, DicConfig dicConfig) {

        List<DicData> dicDatas = loadMainDicData(dicType);
        List<DicData> exDicDatas = loadExDicData(dicType, dicConfig);
        if (exDicDatas != null) {
            dicDatas.addAll(exDicDatas);
        }
        return dicDatas;
    }

    /**
     * @param dicType
     * @return java.util.List<com.kylinhunter.nlp.Config.core.loader.bean.DicData>
     * @throws
     * @title loadMainDicData
     * @description
     * @author BiJi'an
     * @updateTime 2022-04-18 15:05
     */
    protected List<DicData> loadMainDicData(DicType dicType) {
        InputStream in = null;
        String path = dicType.getConfigPath();

        try {
            List<DicData> dicDatas = new ArrayList<>();
            InputStream input = ResourceHelper.getInputStreamInClassPath(path);
            EasyExcel.read(input, DicData.class, new PageReadListener<DicData>(dataList -> {
                dicDatas.addAll(dataList);
            })).sheet().doRead();
            log.info("load loadMainDicData,size={}", dicDatas.size());
            return dicDatas;
        } catch (Exception e) {
            throw new RuntimeException("loadMainDicData error,for:" + path, e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    /**
     * @param
     * @return java.util.List<com.kylinhunter.nlp.Config.core.loader.bean.DicData>
     * @throws
     * @title loadExDicData
     * @description
     * @author BiJi'an
     * @updateTime 2022-04-18 01:14
     */
    protected List<DicData> loadExDicData(DicType dicType, DicConfig dicConfig) {
        DicDataSourceLocal dicDataSourceLocal = config.getDataSource().getLocal();
        File file = new File(dicDataSourceLocal.getExDicDir(), dicConfig.getExDic());
        if (file == null || !file.exists()) {
            return null;
        }
        try {
            List<DicData> dicDatas = new ArrayList<>();
            EasyExcel.read(file, DicData.class, new PageReadListener<DicData>(dataList -> {
                dicDatas.addAll(dataList);
            })).sheet().doRead();
            log.info("load loadExDicData,size={}", dicDatas.size());
            return dicDatas;

        } catch (Exception e) {
            throw new RuntimeException("loadExDicData error", e);
        }
    }

    /**
     * @return void
     * @throws
     * @title tryMonitorExDic
     * @description
     * @author BiJi'an
     * @updateTime 2022-04-18 01:13
     */
    public void tryMonitorExDic() {
        //
        //        Map<DicType, File> exDicNames = dicOption.getExDicNames();
        //        if (exDicNames != null && exDicNames.size() > 0) {
        //            exDicNames.forEach((dicType, file) -> {
        //                monitor(dicType, file);
        //            });
        //        }

    }

    /**
     * @return void
     * @throws
     * @title writeDemo
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 22:58
     */
    public static void writeDemo() {
        File file = UserDirUtils.getTmpDir("dic_test.xlsx");
        log.info("write to {}", file.getAbsolutePath());
        EasyExcel.write(file, DicData.class)
                .sheet("Config")
                .doWrite(() -> {
                    List<DicData> list = ListUtils.newArrayList();
                    for (int i = 0; i < 10; i++) {
                        DicData dicData = new DicData();
                        dicData.setWords("words" + i);
                        dicData.setSecondaryWords("secondaryWords" + i);
                        dicData.setRelationWords("relationWords" + i);
                        dicData.setHitMode(HitMode.HIGH.name());
                        dicData.setType(1);
                        dicData.setClassId(1);
                        list.add(dicData);
                    }
                    return list;
                });

    }

}
