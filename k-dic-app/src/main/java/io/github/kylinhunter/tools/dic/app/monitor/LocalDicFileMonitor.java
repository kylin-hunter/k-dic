package io.github.kylinhunter.tools.dic.app.monitor;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import io.github.kylinhunter.tools.dic.app.config.Config;
import io.github.kylinhunter.tools.dic.app.config.DicConfig;
import io.github.kylinhunter.tools.dic.app.config.LoadConfigLocal;
import io.github.kylinhunter.tools.dic.app.constants.DicType;

import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.io.ResourceHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 10:41
 **/
@Slf4j
public class LocalDicFileMonitor {
    FileAlterationMonitor monitor;
    Config config;
    private boolean started = false;

    public LocalDicFileMonitor(Config config) {
        this.config = config;
    }

    /*
     * @description  start
     * @date  2022/1/22 1:53
     * @author  BiJi'an
     * @Param:
     * @return void
     */
    public synchronized void start() {
        try {
            LoadConfigLocal loadConfigLocal = config.getLoad().getLocal();
            if (!started && loadConfigLocal.isAutoScan()) {

                File exDicPath = ResourceHelper.getFile(loadConfigLocal.getDicPath());
                if (exDicPath.exists()) {
                    log.info("monitor path:" + exDicPath.getAbsolutePath());
                    Map<String, DicType> fileToDicType = config.getDics().values().stream()
                            .collect(Collectors.toMap(DicConfig::getDic, DicConfig::getType));
                    long interval = TimeUnit.SECONDS.toMillis(10);
                    FileAlterationObserver observer = new FileAlterationObserver(exDicPath,
                            (pathname) -> fileToDicType.containsKey(pathname.getName()));
                    observer.addListener(new ExDicAlterationListener(fileToDicType));
                    this.monitor = new FileAlterationMonitor(interval, observer);
                } else {
                    log.error("invalid  dic path" + exDicPath.getAbsolutePath());
                }

                monitor.start();
                started = true;
                log.info(" ex dic monitor started...... ");
            }
        } catch (Exception e) {
            throw new InitException("init local dic monitor error", e);
        }
    }

}
