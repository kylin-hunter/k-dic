package com.kylinhunter.nlp.dic.core.dic.bean;

import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.kylinhunter.nlp.dic.core.dictionary.group.bean.HitMode;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
@Data
public class DicData implements Serializable {
    private static final long serialVersionUID = 1L;
    @ExcelProperty(value = "分类", index = 0)
    private int classId;
    @ExcelProperty(value = "主词", index = 1)
    private String words;
    @ExcelProperty(value = "辅助词", index = 2)
    private String assistWords;
    @ExcelProperty(value = "目标词", index = 3)
    private String relationWords;

    @ExcelIgnore
    private HitMode hitMode;
    @ExcelIgnore
    private int type;

}
