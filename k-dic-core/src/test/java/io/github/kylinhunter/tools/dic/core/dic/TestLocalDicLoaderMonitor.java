package io.github.kylinhunter.tools.dic.core.dic;

import io.github.kylinhunter.tools.dic.core.dic.constants.DicType;

class TestLocalDicLoaderMonitor {

    public static void main(String[] args) {

        LocalDicLoader.getInstance().load(DicType.SENSITIVE);
    }
}