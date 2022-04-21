package com.kylinhunter.nlp.dic.core.loader;

import java.util.List;

import com.kylinhunter.nlp.dic.core.loader.bean.DicData;
import com.kylinhunter.nlp.dic.core.loader.common.AbstractDicLoader;
import com.kylinhunter.nlp.dic.core.config.DicConfig;
import com.kylinhunter.nlp.dic.core.loader.constants.DicType;

import lombok.extern.slf4j.Slf4j;

@Slf4j

public class DBDicLoader extends AbstractDicLoader {
    public DBDicLoader() {
        super();
    }

    @Override
    protected List<DicData> loadDicData(DicType dicType, DicConfig dicConfig) {
        return null;
    }


}
