package com.zhangyue.test.nashor.framework.zookeeper.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author YanMeng
 * @date 16-9-19
 */
public class CuratorTest {


    public static void main(String[] args) {

        CuratorFramework client = null;

        String zkConnect = "";

        RetryPolicy policy = new ExponentialBackoffRetry(1000, Integer.MAX_VALUE);
        client = CuratorFrameworkFactory.builder().connectString(zkConnect)
                .sessionTimeoutMs(30000)
                .connectionTimeoutMs(30000)
                .canBeReadOnly(false)
                .retryPolicy(policy)
                        // .namespace(namespace)
                .defaultData(null)
                .build();
        client.start();



    }
}
