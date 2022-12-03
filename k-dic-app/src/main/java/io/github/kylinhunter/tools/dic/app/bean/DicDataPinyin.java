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
public class DicDataPinyin extends AbstractDicData {
    private static final long serialVersionUID = 1L;
    @ExcelProperty(value = "pinyin", index = 0)
    private String words;
    @ExcelProperty(value = "relationWords", index = 1)
    private String relationWords;

}
