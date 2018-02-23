package com.zhangyue.test.nashor.java.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

/**
 * @author YanMeng
 * @date 16-10-31
 */
public class AtomicIntegerTest {

    @Test
    public void test(){
        AtomicInteger integer = new AtomicInteger();
        integer.addAndGet(123);
    }
}
