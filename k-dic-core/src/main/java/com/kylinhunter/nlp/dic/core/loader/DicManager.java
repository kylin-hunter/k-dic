package com.kylinhunter.nlp.dic.core.loader;

import com.kylinhunter.nlp.dic.core.dic.Dic;
import com.kylinhunter.nlp.dic.core.loader.constants.DicType;
import com.kylinhunter.nlp.dic.core.loader.wrapper.DicWrapper;
import lombok.Data;

/**
 * @author bijian
 * @version 1.0
 * @description TODO
 * @date 2022/4/22 2:36
 */
@Data
public class DicManager {
    protected static DicWrapper[] dics = new DicWrapper[DicType.values().length];


    /*
     * @description  TODO
     * @date  2022/4/22 3:16
     * @author  BiJi'an
     * @Param  dicType
     * @return com.kylinhunter.nlp.dic.core.dic.Dic
     */
    public static Dic get(DicType dicType) {
        return get(dicType, true);
    }

    /*
     * @description  TODO
     * @date  2022/4/22 3:16
     * @author  BiJi'an
     * @Param dicType
     * @Param loadIfNull
     * @return com.kylinhunter.nlp.dic.core.loader.wrapper.DicWrapper
     */
    public static DicWrapper get(DicType dicType, boolean loadIfNull) {
        DicWrapper dic = dics[dicType.ordinal()];
        if (dic == null && loadIfNull) {
            synchronized (DicManager.class) {
                dic = dics[dicType.ordinal()];
                if (dic == null) {
                    dic = new DicWrapper(LocalDicLoader.getInstance().load(dicType));
                    dics[dicType.ordinal()] = dic;
                }
            }
        }
        return dic;
    }
}
