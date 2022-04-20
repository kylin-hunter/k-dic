package com.kylinhunter.nlp.dic.core.loader.monitor;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import com.kylinhunter.nlp.dic.commons.exception.internal.KInitException;
import com.kylinhunter.nlp.dic.core.config.Config;
import com.kylinhunter.nlp.dic.core.config.DicDataSourceLocal;
import com.kylinhunter.nlp.dic.core.loader.constants.DicType;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2022-01-01 10:41
 **/
public class LocalDicFileMonitor {
    FileAlterationMonitor monitor;

    public LocalDicFileMonitor(Config config) {
        DicDataSourceLocal dicDataSourceLocal = config.getDataSource().getLocal();
        File exDicPath = new File(dicDataSourceLocal.getExDicDir());

        Map<String, DicType> fileToDicType =
                config.getDics().values().stream().collect(Collectors.toMap(e -> e.getExDic(), e -> e.getType()));
        long interval = TimeUnit.SECONDS.toMillis(10);
        FileAlterationObserver observer = new FileAlterationObserver(exDicPath,
                (pathname) -> fileToDicType.containsKey(pathname.getName()));
        observer.addListener(new ExDicPathFileListener(fileToDicType));
        monitor = new FileAlterationMonitor(interval, observer);

    }

    public void start() {
        try {
            monitor.start();
        } catch (Exception e) {
            throw new KInitException("init local dic monitor error", e);
        }
    }

}
