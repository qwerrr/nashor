package com.zhangyue.test.nashor.framework.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.ZooKeeper;

import com.zhangyue.test.nashor.framework.zookeeper.common.ZkConnFactory;

/**
 * zookeeper stat 测试
 * @author YanMeng
 * @date 16-8-9
 */
public class ZookeeperStatTest {
    public static void main(String[] args) throws IOException {
        ZooKeeper zooKeeper = ZkConnFactory.getZk();

    }
}
