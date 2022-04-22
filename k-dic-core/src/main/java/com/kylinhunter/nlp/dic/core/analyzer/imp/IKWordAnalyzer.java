package com.kylinhunter.nlp.dic.core.analyzer.imp;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.kylinhunter.nlp.dic.core.analyzer.AbstractWordAnalyzer;
import com.kylinhunter.nlp.dic.core.analyzer.bean.Words;

import lombok.extern.slf4j.Slf4j;

/**
 * @description  a implementation based on ik
 * @author  BiJi'an
 * @date 2022-01-01 19:26
 **/
@Slf4j
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
