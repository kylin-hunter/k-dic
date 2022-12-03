package io.github.kylinhunter.tools.dic.app.monitor;

import java.io.File;
import java.util.Map;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;

import io.github.kylinhunter.tools.dic.app.LocalDicLoader;
import io.github.kylinhunter.tools.dic.app.constants.DicType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022-01-01 23:51
 **/
@Slf4j
@Data
@AllArgsConstructor
public class ExDicAlterationListener implements FileAlterationListener {
    Map<String, DicType> fileToDicType;

    @Override
    public void onStart(FileAlterationObserver fileAlterationObserver) {
//        log.info("monitor start scan files..");
    }

    @Override
    public void onStop(FileAlterationObserver fileAlterationObserver) {
//        log.info("monitor stop scanning..");
    }

    @Override
    public void onDirectoryCreate(File file) {
        log.info(file.getName() + " director created.");
    }

    @Override
    public void onDirectoryChange(File file) {
        log.info(file.getName() + " director changed.");
    }

    @Override
    public void onDirectoryDelete(File file) {
        log.info(file.getName() + " director deleted.");
    }

    @Override
    public void onFileCreate(File file) {
        log.info(file.getName() + " created.");
    }

    @Override
    public void onFileChange(File file) {
        log.info(file.getName() + " changed.");
        DicType dicType = fileToDicType.get(file.getName());
        if (dicType != null) {
            try {
                LocalDicLoader.getInstance().reload(dicType);
            } catch (Exception e) {
                log.error("reload error", e);
            }
        }
    }

    @Override
    public void onFileDelete(File file) {
        log.info(file.getName() + " deleted.");
    }

}
