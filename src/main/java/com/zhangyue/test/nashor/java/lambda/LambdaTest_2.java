package com.zhangyue.test.nashor.java.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @desc:
 *  lambda格式 - 一般格式/简化格式
 *
 * @author: YanMeng
 * @date: 2019-05-17
 */
public class LambdaTest_2 {

    /**
     * 不使用lambda的示例
     */
    public void foo(){
        List<String> names = new ArrayList<>();
        names.add("ZhangSan");
        names.add("LiSi");
        List<String> lowercaseNames = new ArrayList<>();
        for (String name : names) {
            lowercaseNames.add(name.toLowerCase());
        }
    }

    /**
     * 使用lambda的示例
     *
     * lambda表达式一般格式:
     *
     *     (Type1 param1, Type2 param2, ..., TypeN paramN) -> {
     *         expression1;
     *         expression2;
     *         //.............
     *         return x;
     *     }
     */
    public void foo1(){
        List<String> names = new ArrayList<>();
        names.add("ZhangSan");
        names.add("LiSi");

        List<String> lowercaseNames = names.stream()
                .map((String name) -> {
                    return name.toLowerCase();
                })
                .collect(Collectors.toList());
    }


    /**
     * 使用lambda的简化格式的示例
     */
    public void foo2(){
        List<String> names = new ArrayList<>();
        names.add("ZhangSan");
        names.add("LiSi");
        List<String> lowercaseNames = null;

        // 简化参数类型
        lowercaseNames = names.stream()
                .map((name) -> {return name.toLowerCase();})
                .collect(Collectors.toList());

        // 简化参数列表的括号, 参数列表为空不可简化
        lowercaseNames = names.stream()
                .map(name -> {return name.toLowerCase();})
                .collect(Collectors.toList());

        // 简化return和方法体大括号
        lowercaseNames = names.stream()
                .map(name -> name.toLowerCase())
                .collect(Collectors.toList());

        // Method Reference简化
        lowercaseNames = names.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }
}
