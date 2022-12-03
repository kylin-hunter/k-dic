package io.github.kylinhunter.tools.dic.core.dic;

import io.github.kylinhunter.tools.dic.core.match.DicMatch;
import io.github.kylinhunter.tools.dic.core.dic.constants.DicType;

public interface DicLoader {
    DicMatch load(DicType dicType);

    void reload(DicType dicType);
}
