package io.github.kylinhunter.tools.dic.app.imp;

import io.github.kylinhunter.tools.dic.app.bean.DicWordSensitive;
import io.github.kylinhunter.tools.dic.app.constant.DicType;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-04 23:23
 **/

public class DicSensitive extends AbstractDic<DicWordSensitive> {

    public DicSensitive() {
        super(DicType.SENSITIVE);
    }
}
