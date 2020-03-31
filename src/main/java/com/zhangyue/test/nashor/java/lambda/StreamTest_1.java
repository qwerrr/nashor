package com.zhangyue.test.nashor.java.lambda;

import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @desc:
 *
 *  Stream - Stream是元素的集合，这点让Stream看起来用些类似Iterator; 可以支持顺序和并行的对原Stream进行汇聚的操作;
 *
 * @author: YanMeng
 * @date: 2019-05-16
 */
public class StreamTest_1 {

    public void foo(){
        List<Integer> nums = Lists.newArrayList(1, null, 3, 4, null, 6);

        nums.stream()                                   // 创建Stream;
            .filter(num -> num != null)                 // 转换Stream, 每次转换原有Stream对象不改变, 返回一个新的Stream对象
            .count();                                   // reduce聚合
    }

    /**
     * 创建Stream
     */
    public void foo1(){
        List<Integer> nums = Lists.newArrayList(1, null, 3, 4, null, 6);

        // 1. Collection集合的stream方法
        nums.stream();

        // 2. Stream的静态方法
        Stream.of(1, null, 3, 4, null, 6);
        Stream.generate(Math::random);                      // 无限长的stream, 需搭配limit使用
        Stream.iterate(1, item -> item + 1);          // 无限长的stream, 需搭配limit使用
    }

    /**
     * 转换Stream
     */
    public void foo2(){
        List<Integer> nums = Lists.newArrayList(1, null, 3, 4, null, 6);

        // 常用转换
        nums.stream()
            .distinct()                             // 去重
            .filter(num -> num != null)             // 过滤
            .sorted(Comparator.reverseOrder())      // 排序
            .sorted((num1, num2) -> num2 - num1)    // 自定义排序
            .map(String::valueOf)                   // 转换
            .limit(3)                               // 截取前n条
            .collect(Collectors.toList());

        // flatMap
        List<Integer> nums_ = Lists.newArrayList(7, null, 9, 10, null, 12);
        Stream.of(nums, nums_)
            .flatMap(list -> list.stream())
            .collect(Collectors.toList());

        // 延迟执行问题
    }

    /**
     * 聚合(reduce)
     */
    public void foo3(){
        List<Integer> nums = Lists.newArrayList(1, null, 3, 4, null, 6);

        Stream<Integer> s = nums.stream()
            .filter(num -> num != null);

        s.collect(Collectors.toList());                                 // 聚合为list
        s.collect(Collectors.toSet());                                  // 聚合为set
        s.collect(Collectors.toMap(num -> num, Function.identity()));   // 聚合为map
        s.count();                                                      // 计数
        s.toArray();                                                    // 转化为数组[]

        LongStream ls = s.mapToLong(num -> Long.valueOf(num.longValue()));

        // LongStream/IntStream/DoubleStream 特殊聚合方法
        ls.sum();                                                       // 取和值
        ls.average().orElse(0);                                   // 取平均值
    }

    /**
     * stream其他常用api
     */
    public void foo4(){
        List<Integer> nums = Lists.newArrayList(1, null, 3, 4, null, 6);

        Stream s = nums.stream()
                .filter(num -> num != null);

        s.min(Comparator.naturalOrder()).orElse(null);
        s.max(Comparator.naturalOrder()).orElse(null);
        s.findFirst().orElse(null);
        s.findAny().orElse(null);
        s.forEach(num -> {
            // ...
        });
    }
}
