package com.zhangyue.test.nashor.jvm.oomError;

/**
 * @desc 用于测试String.intern()方法在不同版本jvm中表现, 不建议使用
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

        //1.6:false; 1.7:true
        String a = new StringBuffer("计算机").append("软件").toString();
        System.out.println(a == a.intern());

        //1.6:true; 1.7:true
        String b = "我的世界";
        System.out.println(b == b.intern());

        //1.6:false; 1.7:true
        char[] cs = {'q', 'w', 'e', 'r'};
        String c = cs.toString();
        System.out.println(c == c.intern());
    }
}
