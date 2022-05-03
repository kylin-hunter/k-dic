package com.kylinhunter.nlp.dic.core.dic.bean;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BiJi'an
 * @description
 * @create 2022-01-03 01:45
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class DicDataComplete extends AbstractDicData {
    private static final long serialVersionUID = 1L;
    @ExcelProperty(value = "words", index = 0)
    private String words;

}
