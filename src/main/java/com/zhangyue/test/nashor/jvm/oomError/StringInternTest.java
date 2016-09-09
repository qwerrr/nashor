package com.zhangyue.test.nashor.jvm.oomError;

/**
 * @desc 用于测试String.intern()方法在不同版本jvm中表现, 1.6前可以用来1.7以后不建议使用
 *
 * 形如:String a = "abc"; 的声明和初始化被jvm将a存储到了运行时常量池中
 * jvm1.6 的运行时常量池在方法区, 1.7在堆
 *
 * 而StringBuffer和StringBuilder原理都是创建char[], 然后转成String, 此时String放在堆中
 *
 * 所以创建char[]转String的在1.6上会返回地址不同, 1.7则相同
 *
 *
 * @auther YanMeng
 * @data 16-9-9.
 */
public class StringInternTest {

    /*
    差异原因
     */
    public static void main(String[] args) {
        //java中字符串的存储方式-当做常量?有没有指针指向?

        /*************************纯常量,编译后加载时扔到常量池*************************/

        /**
         * 即使多次创建相同值的变量, 也只加载一次
         *  String a = "我的世界";
         *  String b = "我的世界";
         * 第二次声明并初始化时直接从常量池中找, 不会重新开辟空间
         */
        //1.6:true; 1.7:true
        String b = "我的世界";
        System.out.println(b == b.intern());



        System.out.println();
        /*************************char[]转成的*************************/

        /**
         * 1.6
         * a指向堆的某个字符串, intern copy该字符串放入常量池
         * 1.7
         * a指向堆内的常量池, intern 已经存在该常量
         */
        //1.6:false; 1.7:true
        String a = new StringBuffer("qw").append("er").toString();
        System.out.println(a == a.intern());

        /**
         * 1.6
         * c指向堆的某个字符串数组, intern 无法将数据放入常量池
         *
         * 1.7
         * c指向堆内的常量池, intern 已经存在该常量
         */
        //1.6:false; 1.7:true
        char[] cs = {'q', 'w', 'e', 'r'};
        String c = cs.toString();

//        [C@44fd13b5 [C 44fd13b5
//        [C@44fd13b5 java.lang.String 79c79beb
//        [C@44fd13b5 java.lang.String 79c79beb

//        [C@280bca [C 280bca
//        [C@280bca java.lang.String 764fc54e
//        [C@280bca java.lang.String 764fc54e

        System.out.println(cs + " " + cs.getClass().getName() + " " + Integer.toHexString(cs.hashCode()));
        System.out.println(c + " " + c.getClass().getName() + " " + Integer.toHexString(c.hashCode()));
        System.out.println(c.intern() + " " + c.intern().getClass().getName() + " " + Integer.toHexString(c.intern().hashCode()));
        System.out.println(c == c.intern());

        //1.6:true; 1.7:true
        System.out.println(a.intern() == "qwer");

        //1.6:false; 1.7:false
        char[] ii = {'q', 'w', 'e', 'r'};
        String i = ii.toString();
        System.out.println(ii + " " + ii.getClass().getName() + " " + Integer.toHexString(ii.hashCode()));
        System.out.println(i + " " + i.getClass().getName() + " " + Integer.toHexString(i.hashCode()));
        System.out.println(i.intern() + " " + i.intern().getClass().getName() + " " + Integer.toHexString(i.intern().hashCode()));
        System.out.println("qwer".toString() + " " + "qwer".getClass().getName() + " " + Integer.toHexString("qwer".hashCode()));

        System.out.println(i.intern() == c.intern());
        System.out.println(i == c);



        System.out.println();
        /*************************new出来的*************************/

        //1.6:false; 1.7:false
        String d = new String("asdf");
        System.out.println(d == d.intern());

        //1.6:false; 1.7:false
        String e = new String() + "asdf";
        System.out.println(e == e.intern());

        //1.6:true; 1.7:true
        System.out.println(d.intern() == e.intern());

        //1.6:true; 1.7:true
        System.out.println(d.intern() == "asdf");
        //1.6:true; 1.7:true
        System.out.println(e.intern() == "asdf");

        System.out.println("asdf".hashCode() +" "+ d.hashCode());
        System.out.println("asdf".hashCode() +" "+ d.intern().hashCode());

    }
}
