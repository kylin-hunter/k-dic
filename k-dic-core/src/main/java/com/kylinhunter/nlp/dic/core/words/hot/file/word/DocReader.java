package com.kylinhunter.nlp.dic.core.words.hot.file.word;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * @author BiJi'an
 * @description
 * @date 2022-05-14 16:11
 **/
public class DocReader {

    public static String readDoc(InputStream is) throws IOException {
        String resullt = "";
        //首先判断文件中的是doc/docx

        WordExtractor re = new WordExtractor(is);
        resullt = re.getText();
        re.close();

        return resullt;
    }

    public static String readDocx(InputStream is) throws IOException {

        XWPFDocument xwpfDocument = new XWPFDocument(is);
        POIXMLTextExtractor extractor = new XWPFWordExtractor(xwpfDocument);
        String resullt = extractor.getText();
        extractor.close();

        return resullt;
    }

}
