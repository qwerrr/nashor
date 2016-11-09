package com.zhangyue.test.nashor.DeepOfJava.collection;

import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Arrays;

/**
 * @desc
 * @auther YanMeng
 * @data 16-10-16.
 *
 * @see List
 * @see ArrayList
 * @see LinkedList
 * @see Vector
 * @see Arrays
 */
public class ListTest {

    private static boolean isFixedCapacity = false;
    private static int maxCapacity = 100000;

    /**
     * 从0开始依次插入数据
     * @param list
     */
    private static void testAdd(List<Integer> list){
        long begin = System.currentTimeMillis();
        for(int i = 0; i < maxCapacity; i++){
            list.add(i);
        }
        long end = System.currentTimeMillis();
        System.out.println("add time:" + (end - begin));
    }

    /**
     * 随机访问插入一个数字
     * @param list
     */
    private static void testAddOne(List<Integer> list){
        long begin = System.nanoTime();
        list.add(98541, 50000);
        long end = System.nanoTime();
        System.out.println("add one time:" + (end - begin));
    }

    /**
     * 从0开始依次删除数据
     * @param list
     */
    private static void testRemove(List<Integer> list){
        long begin = System.currentTimeMillis();
        for(int i = maxCapacity - 1; i >= 0; i--){
            list.remove(i);
        }
        long end = System.currentTimeMillis();
        System.out.println("remove time:" + (end - begin));
    }

    /**
     * 随机访问删除一个数字
     * @param list
     */
    private static void testRemoveOne(List<Integer> list){
        int result;
        long begin = System.nanoTime();
        result = list.remove(90001);
        long end = System.nanoTime();
        System.out.println("remove one time:" + (end - begin) +", result:"+ result);
    }

    /**
     * 从结尾开始依次更新数据
     * @param list
     */
    private static void testUpdate(List<Integer> list){
        long begin = System.currentTimeMillis();
        for(int i = 0; i < maxCapacity; i++){
            list.set(i, i*2);
        }
        long end = System.currentTimeMillis();
        System.out.println("update time:" + (end - begin));
    }

    /**
     * 从0开始依次get数据
     * @param list
     */
    private static void testGet(List<Integer> list){
        int sum = 0;
        long begin = System.currentTimeMillis();
        for(int i = 0; i < maxCapacity; i++){
            sum += list.get(i);
        }
        long end = System.currentTimeMillis();
        System.out.println("get time:" + (end - begin) +", sum:"+ sum); //输出sum防止被编译优化
    }

    /**
     * @see ArrayList
     * 普通线性表, 默认初始化容量为10
     * 达到容量上限后, (oldCapacity * 3) / 2 + 1
     *
     * ???为何arraylist在 随机访问插入和删除时效率也比linkedlist高???
     *
    add time:12
    update time:11
    get time:9
    remove time:9

    add time:11
    update time:7
    get time:7
    remove time:6

    add time:10
    update time:8
    get time:7
    remove time:5

    remove one time:8546(ns)
    add one time:3799(ns)

    remove one time:9421(ns)
    add one time:4472(ns)

    remove one time:9353(ns)
    add one time:3904(ns)
     *
     */
    @Test
    public void ArrayListTest(){
        ArrayList<Integer> list = new ArrayList<Integer>();

        testAdd(list);
        testUpdate(list);
        testGet(list);
//        testRemove(list);
        testRemoveOne(list);
        testAddOne(list);

        System.out.println(list.size());
    }

    /**
     * @see LinkedList
     * 普通链表
     *
    add time:14
    update time:3700
    get time:3720
    remove time:8

    add time:15
    update time:3528
    get time:3690
    remove time:8

    add time:12
    update time:3606
    get time:3838 730582704
    remove time:9

    remove one time:45592(ns)
    add one time:8982(ns)

    remove one time:49475(ns)
    add one time:9211(ns)

    remove one time:41566(ns)
    add one time:7068(ns)
     */
    @Test
    public void LinkedListTest(){
        LinkedList<Integer> list = new LinkedList<Integer>();
        testAdd(list);
        testUpdate(list);
        testGet(list);
//        testRemove(list);
        testRemoveOne(list);
        testAddOne(list);
        System.out.println(list.size());
    }

    /**
     * 线程安全的线性表
     *
     add time:12
     update time:8
     get time:8
     remove time:7

     add time:12
     update time:8
     get time:8
     remove time:7

     add time:15
     update time:8
     get time:6
     remove time:8

     remove one time:84809(ns)
     add one time:29254(ns)

     remove one time:12618(ns)
     add one time:7810(ns)

     remove one time:9062
     add one time:5228(ns)
     */
    @Test
    public void VectorTest(){
        Vector<Integer> list = new Vector<Integer>();

        new Thread(new VectorUnit(list)).start();   //扔给某个线程, 防止编译器锁优化, fuck, 并没有成功???难道锁优化是在执行期进行的???

        testAdd(list);
        testUpdate(list);
        testGet(list);
        testRemove(list);
//        testRemoveOne(list);
//        testAddOne(list);
        System.out.println(list.size());
    }

    class VectorUnit implements Runnable{
        Vector vector;

        public VectorUnit(Vector vector){
            this.vector = vector;
        }

        public void run() {
            try {
                System.out.println("开始休眠");
                Thread.currentThread().sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("执行前");
            vector.add(123);
            System.out.println("执行后");

            try {
                System.out.println("开始休眠");
                Thread.currentThread().sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            vector.remove(50000);
        }
    }

    @Test
    public void ArraysTest(){

    }
}
