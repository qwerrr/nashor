package com.zhangyue.test.nashor.other.sort;

/**
 * 二分插入排序
 *
 * O(nlogn)
 *
 * @author YanMeng
 * @date 16-11-8
 */
public class BinarySort extends BaseSort{

    public BinarySort(){
        super();
    }

    public BinarySort(int capacity){
        super(capacity);
    }

    @Override
    public void insert(int n){
        if(size == array.length)
            throw new IllegalStateException("size equals capacity, cant insert any more");
        insert(n, 0, size-1);
        size++;
    }

    /**
     * @param n 插入数据
     * @param min 最小下标
     * @param max 最大下标
     */
    private void insert(int n, int min, int max) {
        if(max - min <= 1){
            moveAndInsert(n, min);
        }

        int median = (min + max)/2;
        int medianNum = array[median];

        if(medianNum > n){
            insert(n, min, median);
        }else if(medianNum < n){
            insert(n, median, max);
        } else{
            moveAndInsert(n, median);
        }
    }

    private void moveAndInsert(int n, int index){
        for(int i = size; i > index; i++){
            array[i] = array[i-1];
        }
        array[index+1] = n;
    }
}
