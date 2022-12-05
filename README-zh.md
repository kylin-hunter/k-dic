# k-dic-app

### 介绍

一个词典工具

### 软件架构

根据trie，封装的一个词典工具。

### 安装教程

#### 1、编译并发布到本地

```java
        gradle clean build publishToMavenLocal-x test
```

#### 2、gradle (gradle.org)

```java

        implementation'io.github.kylin-hunter:k-dic-app:1.0.4'

```

#### 3、maven (maven.apache.org)

```java

        <dependency>
          <groupId>io.github.kylin-hunter</groupId>
            <artifactId>k-dic-app</artifactId>
          <version>1.0.4</version>
        </dependency>

```

### 使用说明

#### 1.  敏感词检查

```java
//代码示例

        String text = "北京和北**京和北**啊**京"
        + "北京海淀和北**京海淀和北**啊**京海淀"
        + "河北和河**北和河**啊**北和廊坊和张家口"
        + "乌鲁木齐和乌鲁**木齐和乌鲁**啊**木齐"
        + "呼和浩特和呼和**浩特和呼和**啊**浩特和新疆"
        + "上海和上**海和上**啊**海"
        + "山西和山**西和山**啊**西李小龙和成龙"; // 构造测试文本

        DicSensitive dicSensitive = new DicSensitive();
        // 支持高度命中："北京"
        dicSensitive.add(HitMode.HIGH, "北京");
        // 支持高度命中："北京海淀"
        dicSensitive.add(HitMode.HIGH, "北京海淀");
        // 支持高度命中："河北"，同时还要辅助命中："廊坊", "张家口"
        dicSensitive.add(HitMode.HIGH, "河北", new String[] {"廊坊", "张家口"});
        // 支持高度命中："乌鲁木齐"
        // 支持中度命中："乌鲁**木齐" "乌鲁@#木齐"等等
        dicSensitive.add(HitMode.MIDDLE, "乌鲁木齐");
        // 支持高度命中："呼和浩特"，同时还要辅助命中： "新疆"
        // 支持中度命中："呼和浩特" "乌鲁@#木齐"，同时还要辅助命中： "新疆"
        dicSensitive.add(HitMode.MIDDLE, "呼和浩特", new String[] {"新疆"});
        // 支持高度命中："上海"
        // 支持中度命中："上**海" "上%￥海"等等
        // 支持低度命中："上**啊**海" "上啊海"
        dicSensitive.add(HitMode.LOW, "上海");
        // 支持高度命中："山西" 同时还要辅助命中："成龙", "李小龙"
        // 支持中度命中："山**西" "山%￥西" 同时还要辅助命中："成龙", "李小龙"
        // 支持低度命中："山**啊**西" "山啊西" 同时还要辅助命中："成龙", "李小龙"
        dicSensitive.add(HitMode.LOW, "山西", new String[] {"成龙", "李小龙"});

        List<MatchResult<SensitiveWord>> matchResults = dicSensitive.match(text, FindLevel.HIGH);
        System.out.println("仅高度命中:");
        matchResults.forEach(System.out::println);

        System.out.println("高度命中+中度命中:");
        matchResults = dicSensitive.match(text, FindLevel.HIGH_MIDDLE);
        matchResults.forEach(System.out::println);

        System.out.println("高度命中+中度命中+低度命中:");
        matchResults = dicSensitive.match(text, FindLevel.HIGH_MIDDLE_LOW);
        matchResults.forEach(System.out::println);
```
```java
// 打印 结果
        仅高度命中:
        MatchResult[matchLevel=HIGH, hitWord='北京', hitWordRaw='北京', start=0, end=2, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='北京', hitWordRaw='北京', start=15, end=17, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='北京海淀', hitWordRaw='北京海淀', start=15, end=19, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='河北', hitWordRaw='河北', start=36, end=38, assistedWords=[廊坊, 张家口]]
        MatchResult[matchLevel=HIGH, hitWord='乌鲁木齐', hitWordRaw='乌鲁木齐', start=58, end=62, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='呼和浩特', hitWordRaw='呼和浩特', start=79, end=83, assistedWords=[新疆]]
        MatchResult[matchLevel=HIGH, hitWord='上海', hitWordRaw='上海', start=103, end=105, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='山西', hitWordRaw='山西', start=118, end=120, assistedWords=[成龙, 李小龙]]
        高度命中+中度命中:
        MatchResult[matchLevel=HIGH, hitWord='北京', hitWordRaw='北京', start=0, end=2, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='北京', hitWordRaw='北京', start=15, end=17, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='北京海淀', hitWordRaw='北京海淀', start=15, end=19, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='河北', hitWordRaw='河北', start=36, end=38, assistedWords=[廊坊, 张家口]]
        MatchResult[matchLevel=HIGH, hitWord='乌鲁木齐', hitWordRaw='乌鲁木齐', start=58, end=62, assistedWords=null]
        MatchResult[matchLevel=MIDDLE, hitWord='乌鲁**木齐', hitWordRaw='乌鲁木齐', start=63, end=69, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='呼和浩特', hitWordRaw='呼和浩特', start=79, end=83, assistedWords=[新疆]]
        MatchResult[matchLevel=MIDDLE, hitWord='呼和**浩特', hitWordRaw='呼和浩特', start=84, end=90, assistedWords=[新疆]]
        MatchResult[matchLevel=HIGH, hitWord='上海', hitWordRaw='上海', start=103, end=105, assistedWords=null]
        MatchResult[matchLevel=MIDDLE, hitWord='上**海', hitWordRaw='上海', start=106, end=110, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='山西', hitWordRaw='山西', start=118, end=120, assistedWords=[成龙, 李小龙]]
        MatchResult[matchLevel=MIDDLE, hitWord='山**西', hitWordRaw='山西', start=121, end=125, assistedWords=[成龙, 李小龙]]
        高度命中+中度命中+低度命中:
        MatchResult[matchLevel=HIGH, hitWord='北京', hitWordRaw='北京', start=0, end=2, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='北京', hitWordRaw='北京', start=15, end=17, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='北京海淀', hitWordRaw='北京海淀', start=15, end=19, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='河北', hitWordRaw='河北', start=36, end=38, assistedWords=[廊坊, 张家口]]
        MatchResult[matchLevel=HIGH, hitWord='乌鲁木齐', hitWordRaw='乌鲁木齐', start=58, end=62, assistedWords=null]
        MatchResult[matchLevel=MIDDLE, hitWord='乌鲁**木齐', hitWordRaw='乌鲁木齐', start=63, end=69, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='呼和浩特', hitWordRaw='呼和浩特', start=79, end=83, assistedWords=[新疆]]
        MatchResult[matchLevel=MIDDLE, hitWord='呼和**浩特', hitWordRaw='呼和浩特', start=84, end=90, assistedWords=[新疆]]
        MatchResult[matchLevel=HIGH, hitWord='上海', hitWordRaw='上海', start=103, end=105, assistedWords=null]
        MatchResult[matchLevel=MIDDLE, hitWord='上**海', hitWordRaw='上海', start=106, end=110, assistedWords=null]
        MatchResult[matchLevel=LOW, hitWord='上**啊**海', hitWordRaw='上海', start=111, end=118, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='山西', hitWordRaw='山西', start=118, end=120, assistedWords=[成龙, 李小龙]]
        MatchResult[matchLevel=MIDDLE, hitWord='山**西', hitWordRaw='山西', start=121, end=125, assistedWords=[成龙, 李小龙]]
        MatchResult[matchLevel=LOW, hitWord='山**啊**西', hitWordRaw='山西', start=126, end=133, assistedWords=[成龙, 李小龙]]
```

### 版权 | License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
