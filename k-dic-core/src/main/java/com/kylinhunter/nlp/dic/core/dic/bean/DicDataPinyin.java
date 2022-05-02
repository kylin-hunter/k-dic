package com.kylinhunter.nlp.dic.core.dic.bean;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @create 2022-05-03 01:45
 **/
@Data
public class DicDataPinyin extends AbstractDicData {
    private static final long serialVersionUID = 1L;
    @ExcelProperty(value = "pinyin", index = 0)
    private String words;
    @ExcelProperty(value = "relationWords", index = 1)
    private String relationWords;

}
