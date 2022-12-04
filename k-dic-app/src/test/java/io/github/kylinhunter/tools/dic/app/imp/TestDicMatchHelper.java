package io.github.kylinhunter.tools.dic.app.imp;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;

import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;

/**
 * @author BiJi'an
 * @description
 * @create 2022-01-01 05:42
 **/
public class TestDicMatchHelper {
    public static <T> String[] toStringArr(String text, FindLevel findLevel, List<MatchResult<T>> matchResults) {
        List<String> result = toString(text, findLevel, matchResults);
        if (result != null) {
            return result.toArray(new String[0]);
        }
        return new String[0];
    }

    public static <T> List<String> toString(String text, FindLevel findLevel, List<MatchResult<T>> matchResults) {
        System.out.println("************ print start *********************");
        List<String> matchResultsArr = Lists.newArrayList();
        if (text != null) {
            System.out.println("print[" + findLevel + ":] text:" + text.substring(0, Math.min(10, text.length())));
            if (matchResults != null) {
                System.out.println("print[" + findLevel + ":] result:=>");
                matchResults.sort((Comparator<MatchResult>) (o1, o2) -> {
                    if (o1.getStart() < o2.getStart()) {
                        return -1;
                    } else if (o1.getStart() > o2.getStart()) {
                        return 1;
                    } else {
                        if (o1.getEnd() < o2.getEnd()) {
                            return -1;
                        } else if (o1.getEnd() > o2.getEnd()) {
                            return 1;
                        } else {
                            return o1.getHitWord().compareTo(o2.getHitWord());
                        }
                    }
                });

                matchResultsArr = matchResults.stream()
                        .map(e -> e.getMatchLevel().getCode() + ":" + e.getStart() + ":" + e.getEnd() + ":"
                                + e.getHitWord() + ":" + e.getHitWordRaw() + ":" + Arrays
                                .toString(e.getAssistedWords()))
                        .collect(Collectors.toList());

                matchResultsArr.forEach(System.out::println);

            } else {
                System.out.println("print[" + findLevel + ":] result:" + null);

            }
        } else {
            System.out.println("print[" + findLevel + ":] text:" + null);
        }

        System.out.println("************ print end  *********************\n");

        return matchResultsArr;

    }

}
