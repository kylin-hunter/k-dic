package com.kylinhunter.nlp.dic.core.dic;

import com.kylinhunter.nlp.dic.core.match.DicMatch;
import com.kylinhunter.nlp.dic.core.dic.constants.DicType;

public interface DicLoader {
    DicMatch load(DicType dicType);

    void reload(DicType dicType);
}
