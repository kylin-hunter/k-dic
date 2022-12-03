package io.github.kylinhunter.tools.dic.app.bean;

import java.io.Serializable;

import com.alibaba.excel.annotation.ExcelIgnore;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.HitMode;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
@Data
public class DicData implements Serializable {
    private static final long serialVersionUID = 1L;
    private int classId;
    private String words;
    private String assistWords;
    private String relationWords;
    @ExcelIgnore
    private HitMode hitMode;
    private int type;

}
