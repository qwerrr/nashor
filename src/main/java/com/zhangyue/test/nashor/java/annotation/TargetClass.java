package com.zhangyue.test.nashor.java.annotation;

/**
 * 注解可以修饰:
 *  类/全局变量/局部变量/构造方法/普通方法/参数
 * @author YanMeng
 * @date 16-10-27
 */
@TestAnnotation(id=123,name = "class-annotation",gid = TargetClass.class)
public class TargetClass {

    @TestAnnotation(name = "param-annotation",gid = Integer.class)
    private Integer i;

    @TestAnnotation(name = "constructor-annotation",gid = TargetClass.class)
    public TargetClass(){
    }

    @TestAnnotation(name = "test()-annotation",gid = TargetClass.class)
    public void test(@TestAnnotation(name = "TargetClass",gid = TargetClass.class) String str){  //参数
        Integer v = Integer.parseInt(str);
        this.i = v;
    }

    @TestAnnotation(name = "main()-annotation",gid = TargetClass.class)
    public static void main(String[] args) {

    }
}
