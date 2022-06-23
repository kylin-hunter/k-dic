package com.kylinhunter.nlp.dic.core.words.hot.file.pdf;

import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-05-14 15:07
 **/
@Slf4j
public class PdfReader {

    public static String read(InputStream stream) {

        try {
            PDDocument helloDocument = PDDocument.load(stream);
            PDFTextStripper textStripper = new PDFTextStripper();
            return textStripper.getText(helloDocument);
        } catch (IOException e) {
            log.error("读取文件数据失败，参考信息：" + e.getMessage());
        }
        return "";
    }
}
