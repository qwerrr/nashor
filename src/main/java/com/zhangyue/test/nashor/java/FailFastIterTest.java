package com.zhangyue.test.nashor.java;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YanMeng
 * @date 16-8-16
 */
public class FailFastIterTest {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();

        for(int i = 0; i < 10; i++) list.add(i);

        for(Integer i : list){
            if(i == 5)
                list.remove(0);
            System.out.println(i);
        }

//        for(int i = 0; i < list.size(); i++){
//            if(list.get(i) == 5)
//                list.add(10);
//            System.out.println(list.get(i));
//        }



    }
}
