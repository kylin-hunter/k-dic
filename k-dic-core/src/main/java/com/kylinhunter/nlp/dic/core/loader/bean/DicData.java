package com.kylinhunter.nlp.dic.core.loader.bean;

import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import lombok.Data;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022/1/1
 **/
@Data
public class DicData implements Serializable {
    private static final long serialVersionUID = 1L;
    @ExcelProperty("主词")
    private String words;
    @ExcelProperty("辅助词")
    private String secondaryWords;
    @ExcelProperty("目标词")
    private String relationWords;
    @ExcelProperty("命中类型")
    private String hitMode;
    @ExcelIgnore
    private int type;
    @ExcelIgnore
    private int classId;

}
