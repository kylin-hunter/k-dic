package io.github.kylinhunter.tools.dic.app.excel;

import io.github.kylinhunter.tools.dic.app.constants.DicType;

import io.github.kylinhunter.commons.component.CF;

/**
 * @author BiJi'an
 * @description
 * @create 2022-01-03 01:48
 **/
public class DicDataReaders {

    public static DicDataReader get(DicType dicType) {
        return CF.get(dicType.clazz);
    }
}
