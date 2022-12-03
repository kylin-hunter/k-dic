package io.github.kylinhunter.tools.dic.app;

import io.github.kylinhunter.tools.dic.app.config.Config;
import io.github.kylinhunter.tools.dic.app.config.ConfigHelper;
import io.github.kylinhunter.tools.dic.app.config.LoadConfig;
import io.github.kylinhunter.tools.dic.app.constants.DicType;
import io.github.kylinhunter.tools.dic.app.constants.LoadSource;

import lombok.Data;

/**
 * @author bijian
 * @version 1.0
 * @description DicManager
 * @date 2022/1/22 2:36
 */
@Data
public class DicManager {
    protected static DicAPP[] dicAPPS = new DicAPP[DicType.values().length];
    protected static Config config = ConfigHelper.get();

    /*
     * @description  getDicWrapper
     * @date  2022/1/23 1:12
     * @author  BiJi'an
     * @Param dicType
     * @return io.github.kylinhunter.toolsdic.core.loader.DicAPP
     */
    public static DicAPP get(DicType dicType) {
        return get(dicType, true);
    }

    /*
     * @description  get
     * @date  2022/1/23 0:43
     * @author  BiJi'an
     * @Param dicType
     * @Param loadIfNull
     * @return io.github.kylinhunter.toolsdic.core.loader.DicAPP
     */
    public static DicAPP get(DicType dicType, boolean tryInit) {
        DicAPP dicAPP = dicAPPS[dicType.ordinal()];
        if (dicAPP == null && tryInit) {
            synchronized(DicManager.class) {
                dicAPP = dicAPPS[dicType.ordinal()];
                if (dicAPP == null) {
                    LoadConfig loadConfig = config.getLoad();
                    if (loadConfig.getSource() == LoadSource.LOCAL) {
                        dicAPP = new DicAPP(LocalDicLoader.getInstance().load(dicType));
                    } else {
                        dicAPP = new DicAPP(DBDicLoader.getInstance().load(dicType));
                    }

                    dicAPPS[dicType.ordinal()] = dicAPP;
                }
            }
        }
        return dicAPP;
    }
}
