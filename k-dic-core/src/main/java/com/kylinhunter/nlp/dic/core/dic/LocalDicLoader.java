package com.kylinhunter.nlp.dic.core.dic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.kylinhunter.nlp.dic.commons.exception.internal.KInitException;
import com.kylinhunter.nlp.dic.commons.io.ResourceHelper;
import com.kylinhunter.nlp.dic.core.config.DicConfig;
import com.kylinhunter.nlp.dic.core.config.LoadConfigLocal;
import com.kylinhunter.nlp.dic.core.dic.bean.DicData;
import com.kylinhunter.nlp.dic.core.dic.common.AbstractDicLoader;
import com.kylinhunter.nlp.dic.core.dic.constants.DicType;
import com.kylinhunter.nlp.dic.core.dic.monitor.LocalDicFileMonitor;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.HitMode;
import com.kylinhunter.nlp.dic.core.match.DicMatch;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01
 **/
@Slf4j
@Getter
@Setter
public class LocalDicLoader extends AbstractDicLoader {
    private static LocalDicLoader singleton = new LocalDicLoader();
    private LocalDicFileMonitor localDicFileMonitor = new LocalDicFileMonitor(config);

    private LocalDicLoader() {
        localDicFileMonitor.start();
    }

    public static LocalDicLoader getInstance() {
        return singleton;
    }

    public DicMatch get(DicType dicType) {
        return singleton.load(dicType);
    }

    /**
     * @param dicType dicType
     * @return java.util.List<com.kylinhunter.nlp.Config.core.loader.bean.DicData>
     * @title load
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-01 22:58
     */
    protected List<DicData> loadDicData(DicType dicType, DicConfig dicConfig) {

        List<DicData> dicDatas = loadDefaultDicData(dicType);
        List<DicData> exDicDatas = loadExDicData(dicConfig);
        if (exDicDatas != null) {
            dicDatas.addAll(exDicDatas);
        }
        return dicDatas;
    }

    /**
     * @param dicType dicType
     * @return java.util.List<com.kylinhunter.nlp.Config.core.loader.bean.DicData>
     * @title loadDefaultDicData
     * @description
     * @author BiJi'an
     * @updateTime 2022-01-18 15:05
     */
    protected List<DicData> loadDefaultDicData(DicType dicType) {
        InputStream in = null;
        String path = dicType.getPath();

        try {
            InputStream input = ResourceHelper.getInputStreamInClassPath(path);
            List<DicData> dicDatas = this.readExcel(input);
            log.info("load loadDefaultDicData,size={}", dicDatas.size());
            return dicDatas;
        } catch (Exception e) {
            throw new KInitException("loadDefaultDicData error,for:" + path, e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

    /*
     * @description  loadExDicData
     * @date  2022/1/24 3:04
     * @author  BiJi'an
     * @Param dicType dicType
     * @Param dicConfig dicConfig
     * @return java.util.List<com.kylinhunter.nlp.dic.core.loader.bean.DicData>
     */
    protected List<DicData> loadExDicData(DicConfig dicConfig) {
        try {
            LoadConfigLocal loadConfigLocal = config.getLoad().getLocal();
            File file = new File(loadConfigLocal.getDicPath(), dicConfig.getDic());
            if (!file.exists()) {
                return null;
            }

            List<DicData> dicDatas = readExcel(file);
            log.info("load loadExDicData,size={}", dicDatas.size());
            return dicDatas;

        } catch (Exception e) {
            throw new KInitException("loadExDicData error", e);
        }
    }

    private List<DicData> readExcel(File file) {
        try {
            try (InputStream inputStream = new FileInputStream(file)) {
                return readExcel(inputStream);
            }
        } catch (IOException e) {
            throw new KInitException("readExcel error", e);
        }
    }

    private List<DicData> readExcel(InputStream inputStream) {
        List<DicData> dicDatas = new ArrayList<>();
        ExcelReader excelReader = EasyExcel.read(inputStream).build();
        ReadSheet readSheet1 = EasyExcel.readSheet(0).head(DicData.class)
                .registerReadListener(new PageReadListener<DicData>(e -> {
                    dicDatas.addAll(e);
                    e.forEach(e1 -> e1.setHitMode(HitMode.HIGH));
                })).build();
        ReadSheet readSheet2 = EasyExcel.readSheet(1).head(DicData.class)
                .registerReadListener(new PageReadListener<DicData>(e -> {
                    dicDatas.addAll(e);
                    e.forEach(e1 -> e1.setHitMode(HitMode.MIDDLE));
                })).build();
        ReadSheet readSheet3 = EasyExcel.readSheet(2).head(DicData.class)
                .registerReadListener(new PageReadListener<DicData>(e -> {
                    dicDatas.addAll(e);
                    e.forEach(e1 -> e1.setHitMode(HitMode.LOW));
                })).build();
        excelReader.read(readSheet1, readSheet2, readSheet3);
        excelReader.finish();
        return dicDatas;
    }

}
