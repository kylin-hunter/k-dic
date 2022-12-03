package io.github.kylinhunter.tools.dic.core.dictionary.group;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.tools.dic.core.dictionary.Dictionary;
import io.github.kylinhunter.tools.dic.core.dictionary.DictionaryCreator;
import io.github.kylinhunter.tools.dic.core.dictionary.DictionaryType;
import io.github.kylinhunter.tools.dic.core.dictionary.group.bean.DictionaryNode;
import io.github.kylinhunter.tools.dic.core.dictionary.group.bean.HitMode;
import io.github.kylinhunter.tools.dic.words.analyzer.WordAnalyzer;
import io.github.kylinhunter.tools.dic.words.analyzer.WordAnalyzerType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
@Slf4j
@Getter
@Setter
public class DictionaryGroup<T extends DictionaryNode> {
    private WordAnalyzer wordAnalyzer;
    private boolean assistMatchEnabled = true;
    DictionaryType dictionaryType;
    int unclearSkipMaxLen;
    private Dictionary<T> high;
    private Dictionary<T> middle;
    private Dictionary<T> low;
    private Dictionary<T> highMiddleLow;
    private Dictionary<T> middleLow;

    public DictionaryGroup(WordAnalyzerType wordAnalyzerType,
                           boolean assistMatchEnabled, DictionaryType dictionaryType, int unclearSkipMaxLen) {
        wordAnalyzer = CF.get(wordAnalyzerType.clazz);
        this.assistMatchEnabled = assistMatchEnabled;
        this.dictionaryType = dictionaryType;
        this.unclearSkipMaxLen = unclearSkipMaxLen;
        high = DictionaryCreator.create(dictionaryType, unclearSkipMaxLen);
        middle = DictionaryCreator.create(dictionaryType, unclearSkipMaxLen);
        low = DictionaryCreator.create(dictionaryType, unclearSkipMaxLen);
        highMiddleLow = DictionaryCreator.create(dictionaryType, unclearSkipMaxLen);
        middleLow = DictionaryCreator.create(dictionaryType, unclearSkipMaxLen);
    }

    /**
     * @title put
     * @description
     * @author BiJi'an
     * @date 2022-01-01 03:04
     */
    public void put(T wordNode) {
        HitMode hitMode = wordNode.getHitMode();
        if (hitMode == HitMode.HIGH) {
            high.put(wordNode.getKeyword(), wordNode);
            highMiddleLow.put(wordNode.getKeyword(), wordNode);
            log.info("add hitWord:" + wordNode);

        } else if (hitMode == HitMode.MIDDLE) {
            middle.put(wordNode.getKeyword(), wordNode);
            highMiddleLow.put(wordNode.getKeyword(), wordNode);
            middleLow.put(wordNode.getKeyword(), wordNode);
            log.info("add hitWord:" + wordNode);

        } else if (hitMode == HitMode.LOW) {
            low.put(wordNode.getKeyword(), wordNode);
            highMiddleLow.put(wordNode.getKeyword(), wordNode);
            middleLow.put(wordNode.getKeyword(), wordNode);
            log.info("add hitWord:" + wordNode);
        }

    }

}
