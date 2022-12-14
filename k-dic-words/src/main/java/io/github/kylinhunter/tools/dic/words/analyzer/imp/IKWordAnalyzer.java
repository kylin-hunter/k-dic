package io.github.kylinhunter.tools.dic.words.analyzer.imp;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.tools.dic.words.analyzer.AbstractWordAnalyzer;
import io.github.kylinhunter.tools.dic.words.analyzer.bean.Words;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description a implementation based on ik
 * @date 2022-01-01 19:26
 **/
@Slf4j
@C
public class IKWordAnalyzer extends AbstractWordAnalyzer {
    Analyzer ik = new IKAnalyzer();
    private static final String DEFAULT_FIELD = "f";

    @Override
    public Words analyze(String text) {

        Words words = new Words();
        TokenStream ts = ik.tokenStream(DEFAULT_FIELD, text);
        try {
            ts.reset();
            OffsetAttribute offset = ts.addAttribute(OffsetAttribute.class);
            while (ts.incrementToken()) {
                words.addWord(offset.toString(), offset.startOffset(), offset.endOffset());
            }
            ts.end();
        } catch (IOException e) {
            log.error("analyze error", e);
        } finally {

            IOUtils.closeQuietly(ts);
        }

        return words;
    }
}
