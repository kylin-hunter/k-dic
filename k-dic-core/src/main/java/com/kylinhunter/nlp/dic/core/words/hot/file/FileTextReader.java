package com.kylinhunter.nlp.dic.core.words.hot.file;

import java.io.IOException;
import java.io.InputStream;

import com.kylinhunter.nlp.dic.core.words.hot.file.pdf.PdfReader;
import com.kylinhunter.nlp.dic.core.words.hot.file.word.DocReader;
import com.kylinhunter.plat.commons.exception.inner.KIOException;
import com.kylinhunter.plat.commons.io.ResourceHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-05-14 18:23
 **/
@Slf4j
public class FileTextReader {

    public static String read(String path) {

        log.info("start read {}" + path);
        try (InputStream in = ResourceHelper.getInputStream(path)) {
            String text = "";
            if (path.endsWith(".doc")) {
                text = DocReader.readDoc(in);
            } else if (path.endsWith(".docx")) {
                text = DocReader.readDocx(in);
            } else if (path.endsWith(".pdf")) {
                text = PdfReader.read(in);
            }
            log.info("end read {} 'text.length={}", path, text.length());
            return text;
        } catch (IOException e) {
            throw new KIOException("calculate eror", e);
        }

    }
}
