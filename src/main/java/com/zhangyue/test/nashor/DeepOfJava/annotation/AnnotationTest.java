package com.zhangyue.test.nashor.DeepOfJava.annotation;

/**
 * 注解可以修饰:
 *  类/全局变量/局部变量/构造方法/普通方法/参数
 * @author YanMeng
 * @date 16-10-27
 */
@FirstAnnotation        //类
public class AnnotationTest {

    @FirstAnnotation    //全局变量
    private Integer i;

    @FirstAnnotation    //构造方法
    public AnnotationTest(){
    }

    @FirstAnnotation    //方法
    public void test(@FirstAnnotation String str){  //参数
        @FirstAnnotation    //局部变量
        Integer v = Integer.parseInt(str);

        this.i = v;
    }

    public static void main(String[] args) {

    }
}
