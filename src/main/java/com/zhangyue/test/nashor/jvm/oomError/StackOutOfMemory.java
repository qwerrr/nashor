package com.zhangyue.test.nashor.jvm.oomError;

/**
 * jvm args:-Xss4M
 *
 * @desc 设置栈内存为4M,因为每个线程占用一个虚拟机栈, 所以设置大一些更容易发生栈内存溢出
 * @auther YanMeng
 * @data 16-9-6.
 */
public class StackOutOfMemory {

    /*
    Exception in thread "main" java.lang.OutOfMemoryError: unable to create new native thread
        at java.lang.Thread.start0(Native Method)
        at java.lang.Thread.start(Thread.java:714)
        at com.zhangyue.test.nashor.jvm.oomError.StackOutOfMemory.main(StackOutOfMemory.java:16)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at com.intellij.rt.execution.application.AppMain.main(AppMain.java:144)
    */

    public static void main(String[] args) {
        Object lock = new Object();
        while (true){
            OOMThread oomThread = new OOMThread(lock);
            oomThread.start();
        }
    }

    static class OOMThread extends Thread{

        private Object lock;

        public OOMThread(Object lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock){
                while (true);
            }
        }
    }
}
