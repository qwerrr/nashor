package com.zhangyue.test.nashor.DeepOfJava.annotation;

import java.lang.annotation.*;

/**
 * 元注解:
 * @Target 标示在哪里使用该注解
 *  ElemenetType.CONSTRUCTOR        构造器声明
 *  ElemenetType.FIELD              域声明
 *  ElemenetType.LOCAL_VARIABLE     局部变量声明
 *  ElemenetType.METHOD             方法声明
 *  ElemenetType.PACKAGE            包声明
 *  ElemenetType.PARAMETER          参数声明
 *  ElemenetType.TYPE               类，接口或enum声明
 *  ElemenetType.ANNOTATION_TYPE    注解声明
 *
 * @Retention 标示注解留存到何时
 *  RetentionPolicy.SOURCE          仅在源码中保留, 编译时class后就没有了
 *  RetentionPolicy.CLASS           仅保留到class, 加载到虚拟机后就没有了
 *  RetentionPolicy.RUNTIME         运行时
 *
 * @Documented 标示当前注解加入doc文档中
 *
 * @Inherited 标示当前注解会被继承所传递, 只针对ElemenetType.TYPE类型
 *
 * @author YanMeng
 * @date 16-10-27
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
public @interface TestAnnotation {

    int id() default 0;

    String name();

    Class gid();
}
