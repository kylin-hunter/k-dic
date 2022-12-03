package io.github.kylinhunter.tools.dic.core.dic.excel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import io.github.kylinhunter.tools.dic.core.dic.bean.DicDataSensitive;
import io.github.kylinhunter.tools.dic.core.dictionary.group.bean.HitMode;

/**
 * @author BiJi'an
 * @description
 * @create 2022-01-03 23:08
 **/
public class DicDataReaderForSensitive extends AbstractExcelDicDataReader<DicDataSensitive> {
    @Override
    public List<DicDataSensitive> readExcel(InputStream in) {
        List<DicDataSensitive> dicDataSensitives = new ArrayList<>();
        ExcelReader excelReader = EasyExcel.read(in).build();

        ReadSheet readSheet1 = EasyExcel.readSheet(0).head(DicDataSensitive.class)
                .registerReadListener(new PageReadListener<DicDataSensitive>(e -> {
                    e.forEach(e1 -> e1.setHitMode(HitMode.HIGH));
                    dicDataSensitives.addAll(e);
                })).build();

        ReadSheet readSheet2 = EasyExcel.readSheet(1).head(DicDataSensitive.class)
                .registerReadListener(new PageReadListener<DicDataSensitive>(e -> {
                    e.forEach(e1 -> e1.setHitMode(HitMode.MIDDLE));
                    dicDataSensitives.addAll(e);
                })).build();

        ReadSheet readSheet3 = EasyExcel.readSheet(2).head(DicDataSensitive.class)
                .registerReadListener(new PageReadListener<DicDataSensitive>(e -> {
                    e.forEach(e1 -> e1.setHitMode(HitMode.LOW));
                    dicDataSensitives.addAll(e);
                })).build();

        excelReader.read(readSheet1, readSheet2, readSheet3);
        excelReader.finish();
        return dicDataSensitives;

    }

}
