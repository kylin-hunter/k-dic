package io.github.kylinhunter.tools.dic.core.dic.excel;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.tools.dic.core.dic.bean.AbstractDicData;
import io.github.kylinhunter.tools.dic.core.dic.bean.DicData;
import io.github.kylinhunter.commons.exception.common.KRuntimeException;

/**
 * @author BiJi'an
 * @description
 * @create 2022-01-03 23:05
 **/
public abstract class AbstractExcelDicDataReader<T extends AbstractDicData> implements DicDataReader<T> {

    @Override
    public List<DicData> read(InputStream in) {
        return toDicData(readExcel(in));
    }

    protected abstract List<T> readExcel(InputStream in);

    protected List<DicData> toDicData(List<T> dicDatas) {
        return dicDatas.stream().map(e -> {
            try {
                DicData dicData = new DicData();
                BeanUtils.copyProperties(dicData, e);
                return refine(dicData);
            } catch (Exception ex) {
                throw new KRuntimeException(ex);
            }

        }).filter(e -> e != null).flatMap(Arrays::stream).collect(Collectors.toList());

    }

    DicData[] refine(DicData dicData) throws Exception {
        String words = dicData.getWords();
        if (StringUtils.isEmpty(words)) {
            return null;
        }
        return new DicData[] {dicData};
    }

}
