package com.kylinhunter.nlp.dic.core.dic.monitor;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import com.kylinhunter.nlp.dic.commons.exception.internal.KInitException;
import com.kylinhunter.nlp.dic.core.config.Config;
import com.kylinhunter.nlp.dic.core.config.DicConfig;
import com.kylinhunter.nlp.dic.core.config.LoadConfigLocal;
import com.kylinhunter.nlp.dic.core.dic.constants.DicType;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 10:41
 **/
@Slf4j
public class LocalDicFileMonitor {
    FileAlterationMonitor monitor;
    LoadConfigLocal loadConfigLocal;

    public LocalDicFileMonitor(Config config) {
        this.loadConfigLocal = config.getLoad().getLocal();
        if (loadConfigLocal.isAutoScan()) {
            File exDicPath = new File(loadConfigLocal.getDicPath());
            if (exDicPath.exists()) {
                log.info("monitor path:" + exDicPath.getAbsolutePath());
                Map<String, DicType> fileToDicType =
                        config.getDics().values().stream().collect(Collectors.toMap(DicConfig::getDic,
                                DicConfig::getType));
                long interval = TimeUnit.SECONDS.toMillis(10);
                FileAlterationObserver observer = new FileAlterationObserver(exDicPath,
                        (pathname) -> fileToDicType.containsKey(pathname.getName()));
                observer.addListener(new ExDicAlterationListener(fileToDicType));
                this.monitor = new FileAlterationMonitor(interval, observer);
            } else {
                log.error("invalid  dic path" + exDicPath.getAbsolutePath());
            }

        }

    }

    /*
     * @description  start
     * @date  2022/1/22 1:53
     * @author  BiJi'an
     * @Param:
     * @return void
     */
    public void start() {
        try {
            if (loadConfigLocal.isAutoScan()) {
                monitor.start();
                log.info(" ex dic monitor started...... ");
            }
        } catch (Exception e) {
            throw new KInitException("init local dic monitor error", e);
        }
    }

}
