package io.github.kylinhunter.tools.dic.app.bean;

import io.github.kylinhunter.tools.dic.core.dictionary.bean.WordNode;
import io.github.kylinhunter.tools.dic.core.dictionary.constant.HitMode;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-04 23:21
 **/
@Data
class DicWordSensitiveImp extends WordNode implements DicWordSensitive {

    public DicWordSensitiveImp(HitMode hitMode, String keyword) {
        this.hitMode = hitMode;
        this.word = keyword;
    }

    public DicWordSensitiveImp(HitMode hitMode, String keyword, String[] assistedKeywords) {
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
