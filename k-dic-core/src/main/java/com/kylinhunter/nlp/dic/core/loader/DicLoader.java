package com.kylinhunter.nlp.dic.core.loader;

import com.kylinhunter.nlp.dic.core.dic.DicMatch;
import com.kylinhunter.nlp.dic.core.loader.constants.DicType;

public interface DicLoader {
    DicMatch load(DicType dicType);

    void reload(DicType dicType);
}
