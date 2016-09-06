package com.zhangyue.test.nashor.jvm.oomError;

import java.util.ArrayList;
import java.util.List;

/**
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+HeapDumpOnOutOfMemoryError
 *
 * jvm args:-Xms20M -Xmx20M -XX:+HeapDumpOnOutOfMemoryError
 *
 * @desc 限制堆内存扩展并固定为20M
 *
 * @auther YanMeng
 * @data 16-9-6.
 */
public class HeapOutOfMemory {

    /*
    java.lang.OutOfMemoryError: Java heap space
    Dumping heap to java_pid4156.hprof ... //堆内存在崩溃时被转储到了运行环境根目录的java_pid4156.hprof文件中
    Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        at java.util.Arrays.copyOf(Arrays.java:2245)
        at java.util.Arrays.copyOf(Arrays.java:2219)
        at java.util.ArrayList.grow(ArrayList.java:213)
        at java.util.ArrayList.ensureCapacityInternal(ArrayList.java:187)
        at java.util.ArrayList.add(ArrayList.java:411)
        at com.zhangyue.test.nashor.jvm.oomError.HeapOutOfMemory.main(HeapOutOfMemory.java:23)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:601)
        at com.intellij.rt.execution.application.AppMain.main(AppMain.java:144)
    Heap dump file created [27618625 bytes in 0.122 secs]
     */

    public static void main(String[] args) {

        List<OOMObject> list = new ArrayList<OOMObject>();

        while (true)
            list.add(new OOMObject());
    }

    static class OOMObject{

    }
}
