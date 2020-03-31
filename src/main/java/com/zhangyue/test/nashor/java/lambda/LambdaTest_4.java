package com.zhangyue.test.nashor.java.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @desc:
 *
 *  自定义lambda
 *
 * @author: YanMeng
 * @date: 2019-05-17
 */
public class LambdaTest_4 {

    public void foo(){
        List<String> names = new ArrayList<>();
        names.add("ZhangSan");
        names.add("LiSi");
        List<String> lowercaseNames = null;

        lowercaseNames = forEach(names, str -> str.toLowerCase());
        lowercaseNames = forEach(names, String::toLowerCase);
    }


    public List<String> forEach(List<String> list, Bar_ bar){
        return list.stream()
                .map(bar::doSomething)
                .collect(Collectors.toList());
    }

    /**
     * SAM接口 - Single Abstract Method interfaces
     */
    @FunctionalInterface
    interface Bar {
        String doSomething(String s);
    }



    interface Bar_ {
        String doSomething(String s);
    }
}
