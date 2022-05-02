package com.kylinhunter.nlp.dic.core.dic.excel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.beanutils.BeanUtils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.StringUtils;
import com.kylinhunter.nlp.dic.commons.exception.KRuntimeException;
import com.kylinhunter.nlp.dic.commons.service.KServices;
import com.kylinhunter.nlp.dic.core.dic.bean.DicData;
import com.kylinhunter.nlp.dic.core.dic.bean.DicDataComplete;
import com.kylinhunter.nlp.dic.core.dic.bean.DicDataPinyin;
import com.kylinhunter.nlp.dic.core.dic.bean.DicDataSensitive;
import com.kylinhunter.nlp.dic.core.dic.constants.DicType;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.HitMode;
import com.kylinhunter.nlp.dic.core.pinyin.PinyinService;
import com.kylinhunter.nlp.dic.core.pinyin.PinyinType;

/**
 * @author BiJi'an
 * @description
 * @create 2022-05-03 01:48
 **/
public class DicDataExcelReader {
    private static PinyinService pinyinService = KServices.get(PinyinType.BASIC);

    public static List<DicData> read(DicType type, InputStream inputStream) {
        switch (type) {
            case SENSITIVE:
                return readSensitive(inputStream);
            case COMPLETE: {
                return readComplete(inputStream);

            }
            case PINYIN: {
                return readPinyin(inputStream);

            }

        }
        return Collections.EMPTY_LIST;
    }

    /**
     * @param inputStream
     * @return java.util.List<com.kylinhunter.nlp.dic.core.dic.bean.DicData>
     * @throws
     * @title readPinyin
     * @description
     * @author BiJi'an
     * @updateTime 2022-05-03 03:11
     */
    private static List<DicData> readPinyin(InputStream inputStream) {
        List<DicDataPinyin> dicDatas = new ArrayList<>();
        ExcelReader excelReader = EasyExcel.read(inputStream).build();
        ReadSheet readSheet1 = EasyExcel.readSheet(0).head(DicDataPinyin.class)
                .registerReadListener(new PageReadListener<DicDataPinyin>(e -> {
                    dicDatas.addAll(e);
                    e.forEach(e1 -> e1.setHitMode(HitMode.HIGH));
                })).build();
        excelReader.read(readSheet1);
        excelReader.finish();
        return dicDatas.stream().map(e -> {
            try {
                DicData dicData = new DicData();
                BeanUtils.copyProperties(dicData, e);
                if (StringUtils.isEmpty(dicData.getWords())) {
                    dicData.setWords(pinyinService.toPinyins(dicData.getRelationWords(), 3));
                }
                return dicData;
            } catch (Exception ex) {
                throw new KRuntimeException(ex);
            }

        }).collect(Collectors.toList());
    }

    /**
     * @param inputStream
     * @return java.util.List<com.kylinhunter.nlp.dic.core.dic.bean.DicData>
     * @throws
     * @title read
     * @description
     * @author BiJi'an
     * @updateTime 2022-05-03 03:11
     */
    private static List<DicData> readSensitive(InputStream inputStream) {
        List<DicDataSensitive> dicDatas = new ArrayList<>();
        ExcelReader excelReader = EasyExcel.read(inputStream).build();
        ReadSheet readSheet1 = EasyExcel.readSheet(0).head(DicDataSensitive.class)
                .registerReadListener(new PageReadListener<DicDataSensitive>(e -> {
                    dicDatas.addAll(e);
                    e.forEach(e1 -> e1.setHitMode(HitMode.HIGH));
                })).build();
        ReadSheet readSheet2 = EasyExcel.readSheet(1).head(DicDataSensitive.class)
                .registerReadListener(new PageReadListener<DicDataSensitive>(e -> {
                    dicDatas.addAll(e);
                    e.forEach(e1 -> e1.setHitMode(HitMode.MIDDLE));
                })).build();
        ReadSheet readSheet3 = EasyExcel.readSheet(2).head(DicDataSensitive.class)
                .registerReadListener(new PageReadListener<DicDataSensitive>(e -> {
                    dicDatas.addAll(e);
                    e.forEach(e1 -> e1.setHitMode(HitMode.LOW));
                })).build();
        excelReader.read(readSheet1, readSheet2, readSheet3);
        excelReader.finish();
        return dicDatas.stream().map(e -> {
            try {
                DicData dicData = new DicData();
                BeanUtils.copyProperties(dicData, e);
                return dicData;
            } catch (Exception ex) {
                throw new KRuntimeException(ex);
            }

        }).collect(Collectors.toList());
    }

    /**
     * @param inputStream
     * @return java.util.List<com.kylinhunter.nlp.dic.core.dic.bean.DicData>
     * @throws
     * @title read
     * @description
     * @author BiJi'an
     * @updateTime 2022-05-03 03:11
     */
    private static List<DicData> readComplete(InputStream inputStream) {
        List<DicDataComplete> dicDatas = new ArrayList<>();
        ExcelReader excelReader = EasyExcel.read(inputStream).build();
        ReadSheet readSheet1 = EasyExcel.readSheet(0).head(DicDataComplete.class)
                .registerReadListener(new PageReadListener<DicDataComplete>(e -> {
                    dicDatas.addAll(e);
                    e.forEach(e1 -> e1.setHitMode(HitMode.HIGH));
                })).build();
        ReadSheet readSheet2 = EasyExcel.readSheet(1).head(DicDataComplete.class)
                .registerReadListener(new PageReadListener<DicDataComplete>(e -> {
                    dicDatas.addAll(e);
                    e.forEach(e1 -> e1.setHitMode(HitMode.MIDDLE));
                })).build();
        ReadSheet readSheet3 = EasyExcel.readSheet(2).head(DicDataComplete.class)
                .registerReadListener(new PageReadListener<DicDataComplete>(e -> {
                    dicDatas.addAll(e);
                    e.forEach(e1 -> e1.setHitMode(HitMode.LOW));
                })).build();
        excelReader.read(readSheet1, readSheet2, readSheet3);
        excelReader.finish();
        return dicDatas.stream().map(e -> {
            try {
                DicData dicData = new DicData();
                BeanUtils.copyProperties(dicData, e);
                return dicData;
            } catch (Exception ex) {
                throw new KRuntimeException(ex);
            }

        }).collect(Collectors.toList());
    }
}
