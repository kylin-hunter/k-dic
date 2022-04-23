package com.kylinhunter.nlp.dic.core.loader;

import com.kylinhunter.nlp.dic.core.config.Config;
import com.kylinhunter.nlp.dic.core.config.ConfigHelper;
import com.kylinhunter.nlp.dic.core.config.LoadConfig;
import com.kylinhunter.nlp.dic.core.loader.constants.DicType;
import com.kylinhunter.nlp.dic.core.loader.constants.LoadSource;
import com.kylinhunter.nlp.dic.core.loader.wrapper.DicWrapper;
import lombok.Data;

/**
 * @author bijian
 * @version 1.0
 * @description DicManager
 * @date 2022/4/22 2:36
 */
@Data
public class DicManager {
    protected static DicWrapper[] dics = new DicWrapper[DicType.values().length];
    protected static Config config = ConfigHelper.get();


    /*
     * @description  getDicWrapper
     * @date  2022/4/23 1:12
     * @author  BiJi'an
     * @Param dicType
     * @return com.kylinhunter.nlp.dic.core.loader.wrapper.DicWrapper
     */
    public static DicWrapper getDicWrapper(DicType dicType) {
        return dics[dicType.ordinal()];
    }

    /*
     * @description  get
     * @date  2022/4/23 0:43
     * @author  BiJi'an
     * @Param dicType
     * @Param loadIfNull
     * @return com.kylinhunter.nlp.dic.core.loader.wrapper.DicWrapper
     */
    public static DicWrapper get(DicType dicType) {
        DicWrapper dic = dics[dicType.ordinal()];
        if (dic == null) {
            synchronized (DicManager.class) {
                dic = dics[dicType.ordinal()];
                if (dic == null) {
                    LoadConfig loadConfig = config.getLoad();
                    if (loadConfig.getLoadSource() == LoadSource.LOCAL) {
                        dic = new DicWrapper(LocalDicLoader.getInstance().load(dicType));
                    } else {
                        dic = new DicWrapper(DBDicLoader.getInstance().load(dicType));
                    }

                    dics[dicType.ordinal()] = dic;
                }
            }
        }
        return dic;
    }
}
