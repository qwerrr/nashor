package com.zhangyue.test.nashor.jvm.gc;

/**
 * @desc 测试gc自救, System.gc()并非强制gc,所以失败
 * @auther YanMeng
 * @data 16-9-9.
 */
public class SaveSelf {



    public static void main(String[] args) {

        SaveSelfObject saveSelfObject = new SaveSelfObject();

        saveSelfObject.sayHello();

        //垃圾回收

        System.gc();
        for(int i = 0; i < 10000; i++){
            i++;
        }

        saveSelfObject.sayHello();

        SaveSelfObject.SAVE_SELF_HOOK = null;

        System.gc();

        try {
            saveSelfObject.sayHello();
        }catch (Exception e){
            System.out.println("它已经死了");
            e.printStackTrace();
        }
    }

    static class SaveSelfObject{

        public static SaveSelfObject SAVE_SELF_HOOK = null;

        public void sayHello(){
            System.out.println("我还活着");
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("试图自救");
            SAVE_SELF_HOOK = this;
        }
    }
}
