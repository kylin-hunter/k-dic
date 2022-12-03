package io.github.kylinhunter.tools.dic.app.bean;

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
public class DicDataSensitive extends AbstractDicData {
    private static final long serialVersionUID = 1L;
    @ExcelProperty(value = "分类", index = 0)
    private int classId;
    @ExcelProperty(value = "主词", index = 1)
    private String words;
    @ExcelProperty(value = "辅助词", index = 2)
    private String assistWords;

}
