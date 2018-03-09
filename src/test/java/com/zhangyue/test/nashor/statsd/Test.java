package com.zhangyue.test.nashor.statsd;

import org.junit.Before;

public class Test {

    @Before
    public void before(){
        StatsdClientHolder.init("10.65.0.77", 18125);
    }


    @org.junit.Test
    public void test() throws InterruptedException {
//        StatsdClientHolder.getClient().timing("test.timing.sample", 88000);
//        StatsdClientHolder.getClient().timing("test", 3306);
//        StatsdClientHolder.getClient().timing("test", 8924);


        StatsdClientHolder.getClient().gauge("test.gauge", 10);
        //StatsdClientHolder.getClient().increment("bar", 1);
        //Thread.sleep(1000*10);
        //StatsdClientHolder.getClient().increment("bar", 1);
    }
}
