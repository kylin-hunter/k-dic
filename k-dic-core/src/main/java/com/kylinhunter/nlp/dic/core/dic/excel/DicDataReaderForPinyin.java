package com.kylinhunter.nlp.dic.core.dic.excel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.StringUtils;
import com.kylinhunter.nlp.dic.core.dic.bean.DicData;
import com.kylinhunter.nlp.dic.core.dic.bean.DicDataPinyin;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.HitMode;
import com.kylinhunter.nlp.dic.core.pinyin.PinyinService;
import com.kylinhunter.nlp.dic.core.pinyin.PinyinType;
import com.kylinhunter.plat.commons.service.EServices;

/**
 * @author BiJi'an
 * @description
 * @create 2022-01-03 23:08
 **/
public class DicDataReaderForPinyin extends AbstractExcelDicDataReader<DicDataPinyin> {
    private static PinyinService pinyinService = EServices.get(PinyinType.BASIC);

    @Override
    public List<DicDataPinyin> readExcel(InputStream in) {

        List<DicDataPinyin> dicDataPinyins = new ArrayList<>();
        ExcelReader excelReader = EasyExcel.read(in).build();
        ReadSheet readSheet1 = EasyExcel.readSheet(0).head(DicDataPinyin.class)
                .registerReadListener(new PageReadListener<DicDataPinyin>(e -> {
                    dicDataPinyins.addAll(e);
                    e.forEach(e1 -> e1.setHitMode(HitMode.HIGH));
                })).build();
        excelReader.read(readSheet1); // auto finish if throw exception
        excelReader.finish();
        return dicDataPinyins;

    }

    @Override
    DicData[] refine(DicData dicData) throws Exception {

        String relationWords = dicData.getRelationWords();
        if (!StringUtils.isEmpty(relationWords)) {
            String words = dicData.getWords();
            if (!StringUtils.isEmpty(words)) {
                return new DicData[] {dicData};

            } else {
                String[] pinyins = pinyinService.toPinyins(relationWords, 10);
                if (pinyins == null || pinyins.length <= 0) {
                    DicData[] dicDatas = new DicData[pinyins.length];
                    for (String pinyin : pinyins) {
                        DicData dicDataTmp = new DicData();
                        BeanUtils.copyProperties(dicDataTmp, dicData);
                        dicDataTmp.setWords(pinyin);
                    }
                    return dicDatas;
                }
            }
        }

        return null;

    }

}

