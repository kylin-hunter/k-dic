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

    /**
     * @param hitMode          hit mode  ，
     *                         support three mode ,for example: fox
     *                         HIGH： support whole hit, for example: "fox"
     *                         MIDDLE：support HIGH mode， and also support hit with some special symbols ，for example:
     *                         fo*x、fo@#x
     *                         LOW：support HIGH+MIDDLE mode，  and also support hit other word，for example: "foax"
     *                         "fo*1#x"
     * @param keyword          sensitive word  for example: fox
     * @param assistedKeywords assisted words
     *                         can be nullable
     *                         if the words exist ,those words  must be hit,otherwise, sensitive words will not
     *                         takeeffect
     * @return void
     * @title add a word  to dic
     * @description
     * @author BiJi'an
     * @date 2022-12-07 23:06
     */
    public void add(HitMode hitMode, String keyword, String[] assistedKeywords) {
        this.addWord(new SensitiveWordAdapter(hitMode, keyword, assistedKeywords));
    }

    /**
     * @return void
     * @see #add(HitMode, String, String[])
     */
    public void add(HitMode hitMode, String keyword) {
        this.addWord(new SensitiveWordAdapter(hitMode, keyword));
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
