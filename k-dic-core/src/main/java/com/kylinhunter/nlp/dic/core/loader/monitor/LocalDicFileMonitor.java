package com.kylinhunter.nlp.dic.core.loader.monitor;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import com.kylinhunter.nlp.dic.commons.exception.internal.KInitException;
import com.kylinhunter.nlp.dic.core.config.Config;
import com.kylinhunter.nlp.dic.core.config.LoadConfigLocal;
import com.kylinhunter.nlp.dic.core.loader.constants.DicType;

import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2022-01-01 10:41
 **/
@Slf4j
public class LocalDicFileMonitor {
    FileAlterationMonitor monitor;
    LoadConfigLocal loadConfigLocal;


    public LocalDicFileMonitor(Config config) {
        this.loadConfigLocal = config.getLoad().getLocal();
        if (loadConfigLocal.isAutoScan()) {
            File exDicPath = new File(loadConfigLocal.getExDicDir());
            if (exDicPath.exists()) {
                log.info("monitor path:" + exDicPath.getAbsolutePath());
                Map<String, DicType> fileToDicType =
                        config.getDics().values().stream().collect(Collectors.toMap(e -> e.getExDic(), e -> e.getType()));
                long interval = TimeUnit.SECONDS.toMillis(10);
                FileAlterationObserver observer = new FileAlterationObserver(exDicPath,
                        (pathname) -> fileToDicType.containsKey(pathname.getName()));
                observer.addListener(new ExDicAlterationListener(fileToDicType));
                this.monitor = new FileAlterationMonitor(interval, observer);
            } else {
                log.error("invalid ex dic path" + exDicPath.getAbsolutePath());
            }

        }

    }

    /*
     * @description  start
     * @date  2022/4/22 1:53
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
