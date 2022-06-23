package com.kylinhunter.nlp.dic.core.words.hot;

import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-05-14 20:45
 **/
@Data
public class PinyinData implements Serializable {

    @ExcelProperty(value = "拼音")
    private String pinyin;

    @ExcelProperty(value = "汉字")
    private String hanzi;

}
