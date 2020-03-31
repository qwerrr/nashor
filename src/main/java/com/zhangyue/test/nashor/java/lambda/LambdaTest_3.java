package com.zhangyue.test.nashor.java.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @desc:
 *
 *  方法引用 - 方法引用和构造方法引用
 *      objectName::instanceMethod
 *      ClassName::staticMethod
 *      ClassName::instanceMethod
 *      ClassName::new
 *
 * @author: YanMeng
 * @date: 2019-05-17
 */
public class LambdaTest_3 {

    /**
     * objectName::instanceMethod
     */
    public void foo(){
        List<String> names = new ArrayList<>();
        names.add("ZhangSan");
        names.add("LiSi");
        List<String> lowercaseNames = null;

        LambdaTest_3 lambdaTest = new LambdaTest_3();
        lowercaseNames = names.stream()
                .map(lambdaTest::toLower)
                .collect(Collectors.toList());

        lowercaseNames = names.stream()
                .map(this::toLower)
                .collect(Collectors.toList());
    }


    /**
     * ClassName::staticMethod
     */
    public void foo1(){
        List<String> names = new ArrayList<>();
        names.add("123");
        names.add("456");
        List<Integer> lowercaseNames = null;

        lowercaseNames = names.stream()
                .map(LambdaTest_3::staticToLower)
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }

    /**
     * ClassName::instanceMethod
     */
    public void foo2(){
        List<String> names = new ArrayList<>();
        names.add("ZhangSan");
        names.add("LiSi");
        List<String> lowercaseNames = null;

        lowercaseNames = names.stream()

                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    /**
     * ClassName::new
     */
    public void foo3(){
        List<String> names = new ArrayList<>();
        names.add("123");
        names.add("456");
        List<Integer> intNames = null;

        intNames = names.stream()
                .map(Integer::new)
                .collect(Collectors.toList());
    }





    public String toLower(String s){
        return s.toLowerCase();
    }

    public static String staticToLower(String s){
        return s.toLowerCase();
    }
}
