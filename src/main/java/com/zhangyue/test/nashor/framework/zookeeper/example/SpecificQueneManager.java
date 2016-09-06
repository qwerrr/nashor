package com.zhangyue.test.nashor.framework.zookeeper.example;

/**
 * ZK实现特定队列的管理:
 *  1. 当一个队列的成员都聚齐时，这个队列才可用，否则一直等待所有成员到达，这种是同步队列。
 *  2. 队列按照 FIFO 方式进行入队和出队操作，例如实现生产者和消费者模型。
 *
 * @author YanMeng
 * @date 16-9-2
 */
public class SpecificQueneManager {
}
