package com.kylinhunter.nlp.dic.core.dic;

import com.kylinhunter.nlp.dic.core.config.DicConfig;
import com.kylinhunter.nlp.dic.core.dic.bean.DicData;
import com.kylinhunter.nlp.dic.core.dic.common.AbstractDicLoader;
import com.kylinhunter.nlp.dic.core.dic.constants.DicType;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j

public class DBDicLoader extends AbstractDicLoader {
    private static final DBDicLoader singleton = new DBDicLoader();
 
    private DBDicLoader() {
         
    }

    public static DBDicLoader getInstance() {
        return singleton;
    }

    @Override
    protected List<DicData> loadDicData(DicType dicType, DicConfig dicConfig) {
        return null;
    }


}
