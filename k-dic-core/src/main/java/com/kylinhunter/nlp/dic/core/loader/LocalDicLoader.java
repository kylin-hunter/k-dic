package com.kylinhunter.nlp.dic.core.loader;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.kylinhunter.nlp.dic.commons.io.ResourceHelper;
import com.kylinhunter.nlp.dic.core.config.DicConfig;
import com.kylinhunter.nlp.dic.core.config.LoadConfigLocal;
import com.kylinhunter.nlp.dic.core.match.DicMatch;
import com.kylinhunter.nlp.dic.core.loader.bean.DicData;
import com.kylinhunter.nlp.dic.core.loader.common.AbstractDicLoader;
import com.kylinhunter.nlp.dic.core.loader.constants.DicType;
import com.kylinhunter.nlp.dic.core.loader.monitor.LocalDicFileMonitor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
            List<DicData> dicDatas = new ArrayList<>();
            InputStream input = ResourceHelper.getInputStreamInClassPath(path);
            EasyExcel.read(input, DicData.class, new PageReadListener<DicData>(dicDatas::addAll)).sheet().doRead();
            log.info("load loadDefaultDicData,size={}", dicDatas.size());
            return dicDatas;
        } catch (Exception e) {
            throw new RuntimeException("loadDefaultDicData error,for:" + path, e);
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
            File file = new File(loadConfigLocal.getExDicDir(), dicConfig.getExDic());
            if (!file.exists()) {
                return null;
            }

            List<DicData> dicDatas = new ArrayList<>();
            EasyExcel.read(file, DicData.class, new PageReadListener<DicData>(dicDatas::addAll)).sheet().doRead();
            log.info("load loadExDicData,size={}", dicDatas.size());
            return dicDatas;

        } catch (Exception e) {
            throw new RuntimeException("loadExDicData error", e);
        }
    }


}
