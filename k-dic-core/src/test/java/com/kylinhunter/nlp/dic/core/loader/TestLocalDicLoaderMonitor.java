package com.kylinhunter.nlp.dic.core.loader;

import com.kylinhunter.nlp.dic.core.loader.constants.DicType;

class TestLocalDicLoaderMonitor {

    public static void main(String[] args) {

        LocalDicLoader.getInstance().load(DicType.SENSITIVE);
    }
}