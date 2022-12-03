package io.github.kylinhunter.tools.dic.core.dic.excel;

import java.io.InputStream;
import java.util.List;

import io.github.kylinhunter.tools.dic.core.dic.bean.AbstractDicData;
import io.github.kylinhunter.tools.dic.core.dic.bean.DicData;

/**
 * @author BiJi'an
 * @description
 * @create 2022-01-03 23:05
 **/
public interface DicDataReader<T extends AbstractDicData> {

    List<DicData> read(InputStream in);

}
