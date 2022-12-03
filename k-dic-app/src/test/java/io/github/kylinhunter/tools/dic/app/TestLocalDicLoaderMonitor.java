package io.github.kylinhunter.tools.dic.app;

import io.github.kylinhunter.tools.dic.app.constants.DicType;

class TestLocalDicLoaderMonitor {

    public static void main(String[] args) {

        LocalDicLoader.getInstance().load(DicType.SENSITIVE);
    }
}