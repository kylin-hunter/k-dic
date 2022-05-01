package com.kylinhunter.nlp.dic.core.dic;

import com.kylinhunter.nlp.dic.core.config.Config;
import com.kylinhunter.nlp.dic.core.config.ConfigHelper;
import com.kylinhunter.nlp.dic.core.config.LoadConfig;
import com.kylinhunter.nlp.dic.core.dic.constants.DicType;
import com.kylinhunter.nlp.dic.core.dic.constants.LoadSource;

import lombok.Data;

/**
 * @author bijian
 * @version 1.0
 * @description DicManager
 * @date 2022/1/22 2:36
 */
@Data
public class DicManager {
    protected static Dic[] dics = new Dic[DicType.values().length];
    protected static Config config = ConfigHelper.get();

    /*
     * @description  getDicWrapper
     * @date  2022/1/23 1:12
     * @author  BiJi'an
     * @Param dicType
     * @return com.kylinhunter.nlp.dic.core.loader.Dic
     */
    public static Dic get(DicType dicType) {
        return get(dicType, true);
    }

    /*
     * @description  get
     * @date  2022/1/23 0:43
     * @author  BiJi'an
     * @Param dicType
     * @Param loadIfNull
     * @return com.kylinhunter.nlp.dic.core.loader.Dic
     */
    public static Dic get(DicType dicType, boolean tryInit) {
        Dic dic = dics[dicType.ordinal()];
        if (dic == null && tryInit) {
            synchronized(DicManager.class) {
                dic = dics[dicType.ordinal()];
                if (dic == null) {
                    LoadConfig loadConfig = config.getLoad();
                    if (loadConfig.getSource() == LoadSource.LOCAL) {
                        dic = new Dic(LocalDicLoader.getInstance().load(dicType));
                    } else {
                        dic = new Dic(DBDicLoader.getInstance().load(dicType));
                    }

                    dics[dicType.ordinal()] = dic;
                }
            }
        }
        return dic;
    }
}
