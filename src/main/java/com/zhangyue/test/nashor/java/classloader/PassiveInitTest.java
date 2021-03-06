package com.zhangyue.test.nashor.java.classloader;

/**
 * 被动引用(初始化)测试
 * 通过子类引用父类的静态属性, 子类不会被加载
 *
 * @author YanMeng
 * @date 16-9-27
 */
public class PassiveInitTest {

    /*
        SuperClass static area run
        123
     */
    public static void main(String[] args) {
        System.out.println(SubClass.v); //执行时子类的静态代码块不执行
        System.out.println("=====================");
        new SubClass();
    }
}

class SuperClass{
    static {
        System.out.println("SuperClass static area run");
    }

    public static  int v = 123;
}

class SubClass extends SuperClass{
    static {
        System.out.println("SubClass static area run");
    }
}