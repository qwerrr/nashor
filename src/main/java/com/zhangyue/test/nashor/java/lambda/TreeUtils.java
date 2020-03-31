package com.zhangyue.test.nashor.java.lambda;

import com.google.common.base.Preconditions;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @desc tree结构遍历工具类
 * @date 2018-12-26
 * @developer coderam
 */
public class TreeUtils {

    /**
     * 深度优先正序遍历树的所有节点
     *
     * @param currNode  遍历的开始节点
     * @param consumer  业务逻辑实现
     * @param next      获取下级节点列表实现
     * @param <E> 节点类型
     */
    public static <E> void forEach(E currNode, Consumer<E> consumer, Function<E, List<E>> next){

        Preconditions.checkArgument(currNode != null && consumer != null && next != null,
                "params cant be null!");

        consumer.accept(currNode);
        List<E> children = next.apply(currNode);
        if(children != null && !children.isEmpty()){
            children.forEach(n -> {
                if(n != null) forEach(n, consumer, next);
            });
        }
    }

    /**
     * 深度优先倒序遍历树的所有节点
     * 倒序: 即执行业务逻辑时从叶子节点开始执行, 到根节点结束
     *
     * @param currNode  遍历的开始节点
     * @param consumer  业务逻辑实现
     * @param next      获取下级节点列表实现
     * @param <E> 节点类型
     */
    public static <E> void forEachReverse(E currNode, Consumer<E> consumer, Function<E,List<E>> next){

        Preconditions.checkArgument(currNode != null && consumer != null && next != null,
                "params cant be null!");

        List<E> children = next.apply(currNode);
        if(children != null && !children.isEmpty()){
            children.forEach(n -> {
                if(n != null) forEachReverse(n, consumer, next);
            });
        }
        consumer.accept(currNode);
    }


    /**
     * 深度优先倒序遍历树的所有节点
     * 使用Iterator进行遍历, 因此该方法支持在遍历过程中对节点进行增加或删除
     *
     * @param currNode  遍历的开始节点
     * @param iterator  当前遍历层级的iterator
     * @param consumer  业务逻辑实现
     * @param next      获取下级节点列表实现
     * @param <E> 节点类型
     */
    public static <E> void forEachReverse(E currNode, Iterator iterator, BiConsumer<E, Iterator> consumer, Function<E,List<E>> next){

        Preconditions.checkArgument(currNode != null && iterator != null && consumer != null && next != null,
                "params cant be null!");

        List<E> children = next.apply(currNode);
        if(children != null && !children.isEmpty()){
            Iterator iter = children.iterator();
            while (iter.hasNext()){
                forEachReverse((E)iter.next(), iter, consumer, next);
            }
        }
        consumer.accept(currNode, iterator);
    }
}
