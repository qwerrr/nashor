package com.zhangyue.test.nashor.statsd;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class StatsdDataMaker {

//    @Before
//    public void before(){
//        //StatsdClientHolder.init("10.65.0.77", 18125);
//        StatsdClientHolder.init("127.0.0.1", 8125);
//    }
//
//    @Test
//    public void test(){
//        TimeCtrl   = new ZhouqiTimeCtrl(10);
//    }
//
//
//    /**
//     * 时间控制
//     */
//    interface TimeCtrl {
//        void ctrl() throws InterruptedException;
//    }
//
//    class ZhouqiTimeCtrl implements TimeCtrl{
//
//        //时间间隔
//        private int splitSeconds;
//        public ZhouqiTimeCtrl(int splitSeconds) {
//            this.splitSeconds = splitSeconds;
//            if(splitSeconds <= 0){
//                throw new IllegalArgumentException("splitSeconds必需大于0");
//            }
//        }
//
//        @Override
//        public void ctrl() throws InterruptedException {
//            Thread.currentThread().sleep(splitSeconds*1000);
//        }
//    }
//
//    class RandomTimeCtrl implements TimeCtrl{
//
//        Random random = new Random(System.currentTimeMillis());
//
//        private int randomBegin;
//        private int randomEnd;
//
//        public RandomTimeCtrl(int randomBegin, int randomEnd) {
//            this.randomBegin = randomBegin;
//            this.randomEnd = randomEnd;
//            if(randomEnd <= 0 || randomBegin <=0 || randomEnd - randomBegin <= 0){
//                throw new IllegalArgumentException("randomBegin 和 randomEnd必需大于0, 且randomEnd大于randomBegin");
//            }
//        }
//
//        @Override
//        public void ctrl() throws InterruptedException {
//            Thread.currentThread().sleep(randomBegin+random.nextInt(randomEnd-randomBegin) * 1000);
//        }
//    }
//
//
//    /**
//     * 数据控制
//     */
//    interface DataCtrl<T> {
//        T getData();
//    }
//
//    class RandomIntDataCtrl implements DataCtrl{
//
//        Random random = new Random(System.currentTimeMillis());
//
//        private int randomBegin;
//        private int randomEnd;
//
//        public RandomIntDataCtrl(int randomBegin, int randomEnd) {
//            this.randomBegin = randomBegin;
//            this.randomEnd = randomEnd;
//            if(randomEnd - randomBegin <= 0){
//                throw new IllegalArgumentException("randomEnd大于randomBegin");
//            }
//        }
//
//        @Override
//        public Integer getData() {
//            return randomBegin+random.nextInt(randomEnd-randomBegin);
//        }
//    }
//
//    class SetDataCtrl<T> implements DataCtrl{
//
//        HashMap<T, Integer> map;
//
//        public SetDataCtrl(HashMap<T, Integer> map) {
//            this.map = map;
//
//        }
//
//        @Override
//        public T getData(){
//            return null;
//        }
//    }
//
//    class Range{
//        int begin;
//        int end;
//
//
//    }
}
