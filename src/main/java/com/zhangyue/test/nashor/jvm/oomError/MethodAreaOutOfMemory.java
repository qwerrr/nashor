package com.zhangyue.test.nashor.jvm.oomError;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * jvm args: -XX:PermSize=10M -XX:MaxPermSize=10M
 *
 * @desc 设置方法内存大小为10M, 并且最大为10M
 *  通过cglib动态代理来动态的生成类, 这些方法被cglib内置的classloader自动加到方法区中了
 * @author YanMeng
 * @date 16-9-9
 */
public class MethodAreaOutOfMemory {


    /*
    net.sf.cglib.core.CodeGenerationException: java.lang.reflect.InvocationTargetException-->null
        at net.sf.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:237)
        at net.sf.cglib.proxy.Enhancer.createHelper(Enhancer.java:377)
        at net.sf.cglib.proxy.Enhancer.create(Enhancer.java:285)
        at com.zhangyue.test.nashor.jvm.oomError.MethodAreaOutOfMemory.createProxyObject(MethodAreaOutOfMemory.java:37)
        at com.zhangyue.test.nashor.jvm.oomError.MethodAreaOutOfMemory.main(MethodAreaOutOfMemory.java:21)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
        at java.lang.reflect.Method.invoke(Method.java:597)
        at com.intellij.rt.execution.application.AppMain.main(AppMain.java:140)
    Caused by: java.lang.reflect.InvocationTargetException
        at sun.reflect.GeneratedMethodAccessor1.invoke(Unknown Source)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
        at java.lang.reflect.Method.invoke(Method.java:597)
        at net.sf.cglib.core.ReflectUtils.defineClass(ReflectUtils.java:384)
        at net.sf.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:219)
        ... 9 more
    Caused by: java.lang.OutOfMemoryError: PermGen space
        at java.lang.ClassLoader.defineClass1(Native Method)
        at java.lang.ClassLoader.defineClassCond(ClassLoader.java:631)
        at java.lang.ClassLoader.defineClass(ClassLoader.java:615)
        ... 14 more
    net.sf.cglib.core.CodeGenerationException: java.lang.reflect.InvocationTargetException-->null
    Exception in thread "main" java.lang.OutOfMemoryError: PermGen space
        at java.lang.Throwable.getStackTraceElement(Native Method)
        at java.lang.Throwable.getOurStackTrace(Throwable.java:591)
        at java.lang.Throwable.printStackTrace(Throwable.java:462)
        at java.lang.Throwable.printStackTrace(Throwable.java:451)
        at com.zhangyue.test.nashor.jvm.oomError.MethodAreaOutOfMemory.createProxyObject(MethodAreaOutOfMemory.java:39)
        at com.zhangyue.test.nashor.jvm.oomError.MethodAreaOutOfMemory.main(MethodAreaOutOfMemory.java:21)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
        at java.lang.reflect.Method.invoke(Method.java:597)
        at com.intellij.rt.execution.application.AppMain.main(AppMain.java:140)
     */
    public static void main(String[] args) {
        while (true)
            createProxyObject();
    }

    public static void createProxyObject(){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(targetObject.class);
        enhancer.setUseCache(false);

        enhancer.setCallback(new MethodInterceptor() {
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                return methodProxy.invokeSuper(o, objects);
            }
        });

        try {
            enhancer.create();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    static class targetObject{
    }
}
