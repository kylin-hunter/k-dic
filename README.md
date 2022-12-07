# k-dic-app

### Description

a dictionay tool

### Software Architecture

a dictionary tool according to trie

### Installation
####1、build and publish to local

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

### Instructions

#### 1. check sensitive words

##### 1.1 api

```java
    /**
     * @title  add a word  to dic 
     * @description  
     * @author BiJi'an 
     * @param hitMode  hit mode  ，
     *        support three mode ,for example: fox  
     *        HIGH： support whole hit, for example: "fox" 
     *        MIDDLE：support HIGH mode， and also support hit with some special symbols ，for example: fo*x、fo@#x
     *        LOW：support HIGH+MIDDLE mode，  and also support hit other word，for example: "foax" "fo*1#x"  
     * @param keyword  sensitive word  for example: fox
     * @param assistedKeywords assisted words
     *        can be nullable
     *        if the words exist ,those words  must be hit,otherwise, sensitive words will not takeeffect
     * @date 2022-12-07 23:06
     * @return void
     */
    public void add(HitMode hitMode, String keyword, String[] assistedKeywords) ;

    /**
     * @param inputText the text will be detected
     * @param findLevel findLevel ，support three mode
     *                  HIGH: reference HitMode.HIGH
     *                  HIGH_MIDDLE: reference HitMode.MIDDLE
     *                  HIGH_MIDDLE_LOW: reference HitMode.LOW
     * @return List<MatchResult< SensitiveWord>> the sensitive words ,with offset postion
     * @title check  sensitive word
     * @description
     * @author BiJi'an
     * @date 2022-12-05 02:46
     */
    public List<MatchResult<SensitiveWord>> match(String inputText, FindLevel findLevel) ;

```

##### 1.2 exampe

```java
// Example 
        String text = "kylin and ky**lin and ky**1**lin "
        + " kylinOS and ky**linOS and ky**1**linOS"
        + " teacher and tea**cher and tea*t*cher and male and tall"
        + " fox and fo**x and fo*t*x"
        + " dog and do**g and do**t**g and cat"
        + " movie and mo**vie an mo*t*vie"
        + " star and st**ar and st*t*ar and jackie and bruce"; // text
        DicSensitive dicSensitive = new DicSensitive();
        // support high   hit："kylin"
        dicSensitive.add(HitMode.HIGH, "kylin");
        // support high   hit："kylinOS"
        dicSensitive.add(HitMode.HIGH, "kylinOS");
        // support high   hit："teacher"  , but following words must also hit："male", "tall"
        dicSensitive.add(HitMode.HIGH, "teacher", new String[] {"male", "tall"});
        // support high   hit："fox"
        // support middle hit："fo**x"、 "fo@#x", etc
        dicSensitive.add(HitMode.MIDDLE, "fox");
        // support high   hit："dog"，but following words must also hit： "cat"
        // support middle hit："do**g"、 "do@#g" , etc,but following words must also hit： "cat"
        dicSensitive.add(HitMode.MIDDLE, "dog", new String[] {"cat"});
        // support high   hit："movie"
        // support middle hit："mo**ive" "mo%￥ive", etc
        // support low    hit："mo*a*ive" "mo*b*ive", etc
        dicSensitive.add(HitMode.LOW, "movie");
        // support high   hit："star" , but following words must also hit："jackie", "bruce"
        // support middle hit："st**ar" "st%￥ar" , etc, but following words must also hit："jackie", "bruce"
        // support low    hit："st*a*ar" "st*b*ar" , etc , but following words must also hit："jackie", "bruce"
        dicSensitive.add(HitMode.LOW, "star", new String[] {"jackie", "bruce"});

        List<MatchResult<SensitiveWord>> matchResults = dicSensitive.match(text, FindLevel.HIGH);
        System.out.println("only high hit:");
        matchResults.forEach(System.out::println);

        System.out.println("high hit and middle hit:");
        matchResults = dicSensitive.match(text, FindLevel.HIGH_MIDDLE);
        matchResults.forEach(System.out::println);

        System.out.println("high hit and middle hit and low hit:");
        matchResults = dicSensitive.match(text, FindLevel.HIGH_MIDDLE_LOW);
        matchResults.forEach(System.out::println);
```

##### 1.3 print result

```java
        only high hit:
        MatchResult[matchLevel=HIGH, hitWord='kylin', hitWordRaw='kylin', start=0, end=5, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='kylin', hitWordRaw='kylin', start=34, end=39, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='kylinOS', hitWordRaw='kylinOS', start=34, end=41, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='teacher', hitWordRaw='teacher', start=73, end=80, assistedWords=[male, tall]]
        MatchResult[matchLevel=HIGH, hitWord='fox', hitWordRaw='fox', start=128, end=131, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='dog', hitWordRaw='dog', start=153, end=156, assistedWords=[cat]]
        MatchResult[matchLevel=HIGH, hitWord='movie', hitWordRaw='movie', start=188, end=193, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='star', hitWordRaw='star', start=218, end=222, assistedWords=[jackie, bruce]]
        high hit and middle hit:
        MatchResult[matchLevel=HIGH, hitWord='kylin', hitWordRaw='kylin', start=0, end=5, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='kylin', hitWordRaw='kylin', start=34, end=39, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='kylinOS', hitWordRaw='kylinOS', start=34, end=41, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='teacher', hitWordRaw='teacher', start=73, end=80, assistedWords=[male, tall]]
        MatchResult[matchLevel=HIGH, hitWord='fox', hitWordRaw='fox', start=128, end=131, assistedWords=null]
        MatchResult[matchLevel=MIDDLE, hitWord='fo**x', hitWordRaw='fox', start=136, end=141, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='dog', hitWordRaw='dog', start=153, end=156, assistedWords=[cat]]
        MatchResult[matchLevel=MIDDLE, hitWord='do**g', hitWordRaw='dog', start=161, end=166, assistedWords=[cat]]
        MatchResult[matchLevel=HIGH, hitWord='movie', hitWordRaw='movie', start=188, end=193, assistedWords=null]
        MatchResult[matchLevel=MIDDLE, hitWord='mo**vie', hitWordRaw='movie', start=198, end=205, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='star', hitWordRaw='star', start=218, end=222, assistedWords=[jackie, bruce]]
        MatchResult[matchLevel=MIDDLE, hitWord='st**ar', hitWordRaw='star', start=227, end=233, assistedWords=[jackie, bruce]]
        high hit and middle hit and low hit:
        MatchResult[matchLevel=HIGH, hitWord='kylin', hitWordRaw='kylin', start=0, end=5, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='kylin', hitWordRaw='kylin', start=34, end=39, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='kylinOS', hitWordRaw='kylinOS', start=34, end=41, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='teacher', hitWordRaw='teacher', start=73, end=80, assistedWords=[male, tall]]
        MatchResult[matchLevel=HIGH, hitWord='fox', hitWordRaw='fox', start=128, end=131, assistedWords=null]
        MatchResult[matchLevel=MIDDLE, hitWord='fo**x', hitWordRaw='fox', start=136, end=141, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='dog', hitWordRaw='dog', start=153, end=156, assistedWords=[cat]]
        MatchResult[matchLevel=MIDDLE, hitWord='do**g', hitWordRaw='dog', start=161, end=166, assistedWords=[cat]]
        MatchResult[matchLevel=HIGH, hitWord='movie', hitWordRaw='movie', start=188, end=193, assistedWords=null]
        MatchResult[matchLevel=MIDDLE, hitWord='mo**vie', hitWordRaw='movie', start=198, end=205, assistedWords=null]
        MatchResult[matchLevel=LOW, hitWord='mo*t*vie', hitWordRaw='movie', start=209, end=217, assistedWords=null]
        MatchResult[matchLevel=HIGH, hitWord='star', hitWordRaw='star', start=218, end=222, assistedWords=[jackie, bruce]]
        MatchResult[matchLevel=MIDDLE, hitWord='st**ar', hitWordRaw='star', start=227, end=233, assistedWords=[jackie, bruce]]
        MatchResult[matchLevel=LOW, hitWord='st*t*ar', hitWordRaw='star', start=238, end=245, assistedWords=[jackie, bruce]]

```


### copyright | License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
