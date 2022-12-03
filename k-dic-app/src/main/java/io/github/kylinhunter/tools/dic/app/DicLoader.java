package io.github.kylinhunter.tools.dic.app;

import io.github.kylinhunter.tools.dic.core.match.DictionaryMatcher;
import io.github.kylinhunter.tools.dic.app.constants.DicType;

public interface DicLoader {
    DictionaryMatcher load(DicType dicType);

    void reload(DicType dicType);
}
