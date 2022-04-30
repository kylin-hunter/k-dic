package com.kylinhunter.nlp.dic.core.dic;

import com.kylinhunter.nlp.dic.core.dic.constants.DicType;

class TestLocalDicLoaderMonitor {

    public static void main(String[] args) {

        LocalDicLoader.getInstance().load(DicType.SENSITIVE);
    }
}