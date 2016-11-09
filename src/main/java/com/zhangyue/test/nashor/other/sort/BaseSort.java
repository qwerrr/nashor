package com.zhangyue.test.nashor.other.sort;

/**
 * @author YanMeng
 * @date 16-11-9
 */
public abstract class BaseSort {

    int[] array = null;
    int size = 0;
    static final int DEFAULT_CAPACITY = 100;

    public BaseSort(){
        array = new int[DEFAULT_CAPACITY];
    }

    public BaseSort(int capacity){
        if(capacity <= 0)
            throw new IllegalArgumentException("capacity cant less than zero!");
        array = new int[capacity];
    }

    public String toString(){
        StringBuffer sb = new StringBuffer();
        for (int n : array)
            sb.append(n);
        return sb.toString();
    }

    public abstract void insert(int n);
}
