package com.zhangyue.test.nashor.java.lambda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @desc:
 *
 *  lambda - 一段带有输入参数的可执行语句块
 *
 * @author: YanMeng
 * @date: 2019-05-17
 */
public class LambdaTest_1 {


    public void foo() {

        List<String> names = new ArrayList<>();

        // 使用匿名内部类的写法
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }

    public void foo1(){

        List<String> names = new ArrayList<String>();
        // 使用lambda表达式的写法
        Collections.sort(names, (o1, o2) -> o1.compareTo(o2));


        names.forEach(s -> {
            //asdfasdf
        });
    }
}
