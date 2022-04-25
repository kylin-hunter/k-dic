package com.kylinhunter.nlp.dic.core.match.option;

import com.kylinhunter.nlp.dic.core.match.constant.FindType;
import com.kylinhunter.nlp.dic.core.dictionary.constant.FindLevel;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022-01-01 15:10
 **/

public class MatchOptions {

    public static MatchOption FIRST_PREFIX_HIGH = new MatchOption(FindType.FIRST_PREFIX, FindLevel.HIGH);
    public static MatchOption FIRST_PREFIX_MIDDLE = new MatchOption(FindType.FIRST_PREFIX, FindLevel.HIGH_MIDDLE);
    public static MatchOption FIRST_PREFIX_LOW = new MatchOption(FindType.FIRST_PREFIX, FindLevel.HIGH_MIDDLE_LOW);

    public static MatchOption PREFIX_HIGH = new MatchOption(FindType.PREFIX, FindLevel.HIGH);
    public static MatchOption PREFIX_MIDDLE = new MatchOption(FindType.PREFIX, FindLevel.HIGH_MIDDLE);
    public static MatchOption PREFIX_LOW = new MatchOption(FindType.PREFIX, FindLevel.HIGH_MIDDLE_LOW);

    public static MatchOption FULL_HIGH = new MatchOption(FindType.FULL, FindLevel.HIGH);
    public static MatchOption FULL_MIDDLE = new MatchOption(FindType.FULL, FindLevel.HIGH_MIDDLE);
    public static MatchOption FULL_LOW = new MatchOption(FindType.FULL, FindLevel.HIGH_MIDDLE_LOW);
}
