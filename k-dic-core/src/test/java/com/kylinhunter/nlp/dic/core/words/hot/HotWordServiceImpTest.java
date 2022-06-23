package com.kylinhunter.nlp.dic.core.words.hot;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;

class HotWordServiceImpTest {
    HotWordsService hotWordsService = new HotWordServiceImp();
    String sql = "insert into `cskb_dictionary` "
            + "( `id`,`global`, `words`, `class_id`, `delete_flag`,"
            + " `sys_created`,`action`, `op_lock`, `type`, `secondary_words`, "
            + " `created_user_name`, `sys_updated`, `gid`, `remarks`, `relation_words`,"
            + " `last_edit_user_name`, `agent_id`, `last_edit_user_id`, `created_user_id`, `hit_mode`,"
            + " `last_edit_time`) "
            + " values ('$id', '1', '$pinyin', '0', '0',"
            + "  '$time' ,'0', null, '1', null, "
            + " 'user1', '$time', '0', 'from_bijian', '$hanzi',"
            + " 'user1', '', 't1000000001', 't1000000001', '0',"
            + " '$time');";

    @Test
    void test() throws IOException {

        String path = "/Users/bijian/Documents/项目/cskb-项目资料/中石化/统计热词";
        List<String> hotWords = hotWordsService.calculateDir(path);
        hotWords.forEach(System.out::println);
        Map<String, String> pinyins = hotWordsService.toPinyins(hotWords);

        File pinyinFile = new File("/Users/bijian/Documents/项目/cskb-项目资料/中石化/统计热词-result/拼音.xlsx");

        EasyExcel.write(pinyinFile, PinyinData.class)
                .sheet("拼音")
                .doWrite(() -> {
                    List<PinyinData> list = ListUtils.newArrayList();
                    pinyins.forEach((k, v) -> {
                        PinyinData pinyinData = new PinyinData();
                        pinyinData.setPinyin(k);
                        pinyinData.setHanzi(v);
                        list.add(pinyinData);
                    });

                    return list;
                });

        List<String> pinyinFileData = toPinyinFileData(pinyins);
        pinyinFileData.forEach(System.out::println);


        List<String> sqls = toSql(pinyins);

        sqls.forEach(System.out::println);

        File sqlFile = new File("/Users/bijian/Documents/项目/cskb-项目资料/中石化/统计热词-result/sql.sql");
        FileUtils.writeLines(sqlFile, "UTF-8", sqls);

        System.out.println("hot words==" + hotWords.size());

        System.out.println("pinyins ==" + pinyins.size());

    }

    public static final String FROMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter FORMATTER_DATE_TIME = DateTimeFormatter.ofPattern(FROMAT_DATE_TIME);

    private List<String> toPinyinFileData(Map<String, String> pinyins) {
        List<String> result = Lists.newArrayList();
        pinyins.forEach((k, v) -> result.add(k + "=" + v));
        return result;

    }

    private List<String> toSql(Map<String, String> pinyins) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextTime = now.minus(1, ChronoUnit.HOURS);

        List<String> result = Lists.newArrayList();
        for (Map.Entry<String, String> en : pinyins.entrySet()) {
            String k = en.getKey();
            String v = en.getValue();
            nextTime = nextTime.plus(1, ChronoUnit.SECONDS);

            System.out.println("pinyin=" + k + ",word=" + v);

            String sqlTmp = sql.replace("$id", UUID.randomUUID().toString().replace("-", ""));
            sqlTmp = sqlTmp.replace("$pinyin", k);
            sqlTmp = sqlTmp.replace("$hanzi", v);

            sqlTmp = sqlTmp.replace("$time", FORMATTER_DATE_TIME.format(nextTime));
            result.add(sqlTmp);
        }

        return result;

    }
}