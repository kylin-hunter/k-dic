package com.kylinhunter.nlp.dic.commons.io;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @create 2022-01-03 02:15
 **/
@Data
@AllArgsConstructor
public class PathInfo {
    private PathType pathType;
    private String path;
}
