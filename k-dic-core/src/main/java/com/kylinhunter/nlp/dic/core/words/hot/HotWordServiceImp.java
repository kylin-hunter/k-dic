package com.kylinhunter.nlp.dic.core.words.hot;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Maps;
import com.kylinhunter.nlp.dic.core.pinyin.PinyinService;
import com.kylinhunter.nlp.dic.core.pinyin.PinyinType;
import com.kylinhunter.nlp.dic.core.words.analyzer.WordAnalyzer;
import com.kylinhunter.nlp.dic.core.words.analyzer.WordAnalyzerType;
import com.kylinhunter.nlp.dic.core.words.analyzer.bean.Words;
import com.kylinhunter.nlp.dic.core.words.hot.file.FileTextReader;
import com.kylinhunter.plat.commons.io.ResourceHelper;
import com.kylinhunter.plat.commons.service.EServices;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-05-14 14:41
 **/
@Slf4j
public class HotWordServiceImp implements HotWordsService {
    WordAnalyzer wordAnalyzer = EServices.get(WordAnalyzerType.DEFAULT);
    PinyinService pinyinService = EServices.get(PinyinType.BASIC);

    @Override
    public List<String> calculateDir(String path) {
        Map<String, Integer> result = Maps.newHashMap();
        File file = ResourceHelper.getFile(path);
        if (file.isDirectory()) {
            Collection<File> listFiles = FileUtils.listFiles(file, new String[] {"docx", "doc", "pdf"}, true);
            listFiles.forEach(f -> {
                String text = FileTextReader.read(f.getAbsolutePath());
                calculate(result, text);
            });
        }
        Map<String, Integer> resultDist = calculateHot(result);
        resultDist.forEach((k, v) -> {
            log.info("word={},count={}", k, v);
        });
        return resultDist.keySet().stream().sorted().collect(Collectors.toList());

    }

    @Override
    public Map<String, String> toPinyins(List<String> hotWords) {
        Map<String, String> result = Maps.newLinkedHashMap();
        hotWords.forEach(word -> {
            String[] pinyins = pinyinService.toPinyins(word, 3);
            for (String pinyin : pinyins) {
                result.put(pinyin, word);
            }
        });
        return result;
    }

    @Override
    public List<String> calculateFile(String path) {

        String text = FileTextReader.read(path);

        return Collections.EMPTY_LIST;
    }

    private Map<String, Integer> calculateHot(Map<String, Integer> result) {

        return result.entrySet().stream().filter(en -> {

            if (en.getKey().length() > 1 && en.getValue() > 1) {

                if (LangUtil.isChinese(en.getKey())) {
                    return true;
                }
            }
            return false;

        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void calculate(Map<String, Integer> result, String text) {
        Words words = wordAnalyzer.analyze(text);

        words.forEach(word -> {
            result.compute(word.getText(), (k, v) -> {
                if (v == null) {
                    v = 1;
                } else {
                    v++;
                }
                return v;
            });
        });

    }
}
