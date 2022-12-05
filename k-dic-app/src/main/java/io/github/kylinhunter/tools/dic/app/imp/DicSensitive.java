package io.github.kylinhunter.tools.dic.app.imp;

import io.github.kylinhunter.tools.dic.app.bean.DicWord;
import io.github.kylinhunter.tools.dic.app.constant.DicType;
import io.github.kylinhunter.tools.dic.core.dictionary.bean.WordNode;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.HitMode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-04 23:23
 **/

public class DicSensitive extends AbstractDic<DicSensitive.SensitiveWord, DicSensitive.SensitiveWord> {

    public DicSensitive() {
        super(DicType.SENSITIVE);
    }

    public void add(HitMode hitMode, String keyword) {
        this.addWord(new SensitiveWordAdapter(hitMode, keyword));
    }

    public void add(HitMode hitMode, String keyword, String[] assistedKeywords) {
        this.addWord(new SensitiveWordAdapter(hitMode, keyword, assistedKeywords));
    }

    /**
     * @author BiJi'an
     * @description
     * @date 2022-12-05 00:13
     **/
    public interface SensitiveWord extends DicWord {

    }

    /**
     * @author BiJi'an
     * @description
     * @date 2022-12-04 23:21
     **/
    @EqualsAndHashCode(callSuper = true)
    @Data
    static class SensitiveWordAdapter extends WordNode implements SensitiveWord {

        public SensitiveWordAdapter(HitMode hitMode, String keyword) {
            this.hitMode = hitMode;
            this.word = keyword;
        }

        public SensitiveWordAdapter(HitMode hitMode, String keyword, String[] assistedKeywords) {
            this.hitMode = hitMode;
            this.word = keyword;
            this.assistedWords = assistedKeywords;
        }

        @Override
        public void setWord(String word) {
            this.word = word;
        }

        @Override
        public String getWord() {
            return this.word;
        }
    }
}
