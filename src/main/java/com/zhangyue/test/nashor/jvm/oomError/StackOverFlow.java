package com.zhangyue.test.nashor.jvm.oomError;

/**
 * jvm args:-Xss128k
 *
 * @desc 限制虚拟机栈和本地方法栈的大小为128k
 * @auther YanMeng
 * @data 16-9-6.
 */
public class StackOverFlow {


    /*
    深度:1033
    java.lang.StackOverflowError
        at com.zhangyue.test.nashor.jvm.oomError.StackOverFlow$SOFObject.run(StackOverFlow.java:25)
        at com.zhangyue.test.nashor.jvm.oomError.StackOverFlow$SOFObject.run(StackOverFlow.java:26)
        at com.zhangyue.test.nashor.jvm.oomError.StackOverFlow$SOFObject.run(StackOverFlow.java:26)
        at com.zhangyue.test.nashor.jvm.oomError.StackOverFlow$SOFObject.run(StackOverFlow.java:26)
        .
        .
        .
     */
    public static void main(String[] args) {
        SOFObject sofObject = new SOFObject();
        try {
            sofObject.run();
        }catch (Throwable t){
            System.out.println("深度:"+sofObject.deep);
            t.printStackTrace();
        }
    }

    static class SOFObject{
        public int deep = 0;
        public void run(){
            deep++;
            run();
        }
    }
}
