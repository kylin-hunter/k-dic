package io.github.kylinhunter.tools.dic.core.dic;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;

import io.github.kylinhunter.tools.dic.core.config.DicConfig;
import io.github.kylinhunter.tools.dic.core.config.LoadConfigLocal;
import io.github.kylinhunter.tools.dic.core.dic.bean.DicData;
import io.github.kylinhunter.tools.dic.core.dic.common.AbstractDicLoader;
import io.github.kylinhunter.tools.dic.core.dic.constants.DicType;
import io.github.kylinhunter.tools.dic.core.dic.excel.DicDataReaders;
import io.github.kylinhunter.tools.dic.core.dic.monitor.LocalDicFileMonitor;
import io.github.kylinhunter.tools.dic.core.match.DicMatch;

import io.github.kylinhunter.commons.exception.common.KRuntimeException;
import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.io.ResourceHelper;
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
     * @return java.util.List<io.github.kylinhunter.toolsConfig.core.loader.bean.DicData>
     * @title load
     * @description
     * @author BiJi'an
     * @date 2022-01-01 22:58
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
     * @return java.util.List<io.github.kylinhunter.toolsConfig.core.loader.bean.DicData>
     * @title loadDefaultDicData
     * @description
     * @author BiJi'an
     * @date 2022-01-18 15:05
     */
    protected List<DicData> loadDefaultDicData(DicType dicType) {
        InputStream in = null;
        String path = dicType.getPath();

        try {
            in = ResourceHelper.getInputStreamInClassPath(path);
            List<DicData> dicDatas = DicDataReaders.get(dicType).read(in);
            log.info("load loadDefaultDicData,size={}", dicDatas.size());
            return dicDatas;
        } catch (Exception e) {
            throw new InitException("loadDefaultDicData error,for:" + path, e);
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
     * @return java.util.List<io.github.kylinhunter.toolsdic.core.loader.bean.DicData>
     */
    protected List<DicData> loadExDicData(DicConfig dicConfig) {
        LoadConfigLocal loadConfigLocal = config.getLoad().getLocal();
        InputStream in = null;
        try {
            in = ResourceHelper.getInputStream(loadConfigLocal.getDicPath() + "/" + dicConfig.getDic());
            if (in == null) {
                throw new InitException("invalid path:" + loadConfigLocal.getDicPath() + "/" + dicConfig.getDic());
            }

            List<DicData> dicDatas = DicDataReaders.get(dicConfig.getType()).read(in);
            log.info("load loadExDicData,size={}", dicDatas.size());
            return dicDatas;

        } catch (KRuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new InitException("loadExDicData error", e);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

}
