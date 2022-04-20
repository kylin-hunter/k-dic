package com.kylinhunter.nlp.dic.core.loader;

import com.kylinhunter.nlp.dic.core.dic.Dic;
import com.kylinhunter.nlp.dic.core.loader.constants.DicType;

public interface DicLoader {
    Dic load(DicType dicType);

    Dic load(DicType dicType, boolean force);
}
