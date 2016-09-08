package com.zhangyue.test.nashor.jvm.oomError;

import java.util.ArrayList;
import java.util.List;

/**
 * vm args: -XX:PermSize=10M -XX:MaxPermSize=10M -Xms10M -Xmx10M
 *
 * @desc 设置方法区大小为10M,并且方法区最大为10M; 堆大小为10M,并且最大为10M
 *
 *  hotspot虚拟机的方法区又叫永久代
 *      在1.7版本之前: 存在于方法区 可以被gc所回收
 *      在1.7(包含)之后: 存在于堆
 *  所以需要将方法区和堆均内存均设置小写才能让Error在所有jvm中暴露出来
 *
 * @auther YanMeng
 * @data 16-9-8.
 */
public class ConstantsPoolOutOfMemory {

    public static void main(String[] args) {
        constantsPoolOut();
    }


    /*

    因为常量可以被gc所回收, 所以list持有防止被full gc

    jvm 1.6.0_45下运行:
        Exception in thread "main" java.lang.OutOfMemoryError: PermGen space
            at java.lang.String.intern(Native Method)
            at com.zhangyue.test.nashor.jvm.oomError.ConstantsPoolOutOfMemory.constantsPoolOut(ConstantsPoolOutOfMemory.java:23)
            at com.zhangyue.test.nashor.jvm.oomError.ConstantsPoolOutOfMemory.main(ConstantsPoolOutOfMemory.java:16)
            at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
            at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
            at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
            at java.lang.reflect.Method.invoke(Method.java:597)
            at com.intellij.rt.execution.application.AppMain.main(AppMain.java:144)

    openJdk 1.7.0-internal下运行:
        Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
            at java.lang.Integer.toString(Integer.java:331)
            at java.lang.String.valueOf(String.java:3007)
            at com.zhangyue.test.nashor.jvm.oomError.MethodAreaOutOfMemory.constantsPoolOut(MethodAreaOutOfMemory.java:42)
            at com.zhangyue.test.nashor.jvm.oomError.MethodAreaOutOfMemory.main(MethodAreaOutOfMemory.java:16)
            at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
            at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
            at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
            at java.lang.reflect.Method.invoke(Method.java:601)
            at com.intellij.rt.execution.application.AppMain.main(AppMain.java:144)
     */

    public static void constantsPoolOut(){
        List<String> list = new ArrayList<String>();

        for(int i = 0; i < Integer.MAX_VALUE - 1; i++){
            //"abc".intern(): 查找运行时常量池中是否有值为"abc"的常量, 无add到常量池, 有return常量指针
            list.add(String.valueOf(i).intern());
        }
    }
}
