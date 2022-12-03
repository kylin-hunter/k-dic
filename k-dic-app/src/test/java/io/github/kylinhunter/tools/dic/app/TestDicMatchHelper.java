package io.github.kylinhunter.tools.dic.app;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import io.github.kylinhunter.tools.dic.core.dictionary.constant.FindLevel;
import io.github.kylinhunter.tools.dic.core.match.bean.MatchResult;

/**
 * @author BiJi'an
 * @description
 * @create 2022-01-01 05:42
 **/
public class TestDicMatchHelper {
    public static String[] toStringArr(String text, FindLevel findLevel, List<MatchResult> matchResults) {
        List<String> result = toString(text, findLevel, matchResults);
        if (result != null) {
            return result.toArray(new String[0]);
        }
        return null;
    }

    public static List<String> toString(String text, FindLevel findLevel, List<MatchResult> matchResults) {
        System.out.println("************ print start *********************");
        List<String> matchResultsArr = null;
        if (text != null) {
            System.out.println("print[" + findLevel + ":] text:" + text.substring(0, Math.min(10, text.length())));
            if (matchResults != null) {
                System.out.println("print[" + findLevel + ":] result:=>");
                Collections.sort(matchResults, new Comparator<MatchResult>() {
                    @Override
                    public int compare(MatchResult o1, MatchResult o2) {
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
                    }
                });

                matchResultsArr = matchResults.stream()
                        .map(e -> e.getMatchLevel() + ":" + e.getStart() + ":" + e.getEnd() + ":"
                                + e.getHitWord() + ":" + e.getMatchWord() + ":" + Arrays.toString(e.getAssistWords()))
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
