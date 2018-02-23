package com.zhangyue.test.nashor.java.concurrent;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @desc
 * @auther YanMeng
 * @data 16-8-15.
 */
public class FailFastTest {



    public static void main(String[] args) {

        List<Integer> list = null;

        list = getVector();                 //ConcurrentModificationException
        list = getSyncList();               //ConcurrentModificationException
        list = getCopyOnWriteArrayList();   //OK

        for(int i = 0; i < 10; i++) list.add(i);

        Iterator<Integer> it = list.iterator();
        while (it.hasNext()){
            Integer i = it.next();
            if(i == 5){
                list.add(10);
            }
            System.out.println(i);
        }
    }

    private static List<Integer> getSyncList(){
        List<Integer> list = new ArrayList<Integer>();
        return Collections.synchronizedList(list);
    }

    private static List<Integer> getVector(){
        List<Integer> list = new Vector<Integer>();
        return list;
    }

    private static List<Integer> getCopyOnWriteArrayList(){
        List<Integer> list = new CopyOnWriteArrayList<Integer>();
        return list;
    }
}
