package com.zhangyue.test.nashor.DeepOfJava.concurrent;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 使用
 *  Vector或者Collections.synchronizedList(List)
 * 可以使用一个线程安全的列表
 * 但是两个列表的迭代器均为fail-fast迭代器, 在迭代时修改数据会抛出ConcurrentModificationException
 * 程序员可以自己在迭代前copy一个副本来进行迭代, 以保证迭代的安全, 但是这样效率并不高
 *
 * 而CopyOnWriteArrayList在写入时copy副本, 本身写入的操作要比迭代少的多, 所以该类可以提供更好的性能和并发能力
 *
 * @desc
 * @auther YanMeng
 * @data 16-8-16.
 */
public class CopyOnWriteArrayListTest {
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> arrayList;


    }
}
