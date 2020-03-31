package com.zhangyue.test.nashor.java.lambda;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Objects;
import java.util.Optional;

/**
 * @desc:
 *
 *  Optional - 保存对象类型的值或者null的容器
 *
 * @author: YanMeng
 * @date: 2019-05-17
 */
public class OptionalTest_1 {

    public void foo(){
        Object object = new Object();


        // Optional可以用于做类似于Stream的转换
        Optional.ofNullable(object)
                .filter(o -> o != null)
                .map(Objects::toString)
                .orElse(defaultValue());

        // orElseGet和orElse区别
        Optional.ofNullable(object)
                .filter(o -> o != null)
                .map(Objects::toString)
                .orElseGet(() -> "def");

        Optional.ofNullable(object)
                .filter(o -> o != null)
                .map(Objects::toString)
                .orElseGet(this::defaultValue);
    }

    public void foo1(){
        String param = "";

        // 使用StringUtils实现参数默认值
        StringUtils.defaultIfEmpty(param, "abc");

        // 使用Optional实现参数默认值
        Optional.ofNullable(param)
                .filter(StringUtils::isNotEmpty)
                .orElse("abc");

        // 使用Optional实现复杂判断后的参数默认值
        Optional.ofNullable(param)
                .filter(StringUtils::isNotEmpty)
                .filter(s -> s.length() == 3)
                .filter(NumberUtils::isNumber)
                .map(Long::valueOf)
                .orElse(123L);
    }



    public String defaultValue(){
        return "def";
    }
}
