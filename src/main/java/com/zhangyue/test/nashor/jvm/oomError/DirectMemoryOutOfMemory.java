package com.zhangyue.test.nashor.jvm.oomError;

import sun.misc.Unsafe;

/**
 * jvm args: -XX:MaxDirectMemorySize=10M -Xmx20M
 *
 * @desc 设置直接内存区最大内存为10M, 如果不加 MaxDirectMemorySize设置, 默认为20M(-Xmx指定的堆最大内存)
 *  通过Unsafe类直接申请在直接内存中开辟空间, 但是因为Unsafe本身获取实例的方法普通类无法调用, 所以使用反射获取实例
 *
 * @author YanMeng
 * @date 16-9-9
 */
public class DirectMemoryOutOfMemory {

    private static final int _1MB = 1024 * 1024;


    /*
    Exception in thread "main" java.lang.IllegalAccessException: Class com.zhangyue.test.nashor.jvm.oomError.DirectMemoryOutOfMemory can not access a member of class sun.misc.Unsafe with modifiers "private"
        at sun.reflect.Reflection.ensureMemberAccess(Reflection.java:65)
        at java.lang.Class.newInstance0(Class.java:351)
        at java.lang.Class.newInstance(Class.java:310)
        at com.zhangyue.test.nashor.jvm.oomError.DirectMemoryOutOfMemory.main(DirectMemoryOutOfMemory.java:17)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
        at java.lang.reflect.Method.invoke(Method.java:597)
        at com.intellij.rt.execution.application.AppMain.main(AppMain.java:140)
     */
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
        Unsafe unsafe = (Unsafe) unsafeClass.newInstance();

        int i = 1;
        while (true){
            System.out.println("第"+(i++)+"次执行分配内存");
            unsafe.allocateMemory(_1MB);
        }
    }
}
