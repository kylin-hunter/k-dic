package io.github.kylinhunter.tools.dic.app.excel;

import java.io.InputStream;
import java.util.List;

import io.github.kylinhunter.tools.dic.app.bean.AbstractDicData;
import io.github.kylinhunter.tools.dic.app.bean.DicData;

/**
 * @author BiJi'an
 * @description
 * @create 2022-01-03 23:05
 **/
public interface DicDataReader<T extends AbstractDicData> {

    List<DicData> read(InputStream in);

}
