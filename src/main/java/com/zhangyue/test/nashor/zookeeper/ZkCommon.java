package com.zhangyue.test.nashor.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author YanMeng
 * @date 16-8-9
 */
public class ZkCommon {

    private static String ip = "127.0.0.1";
    private static int port = 2181;

    public static ZooKeeper getZk() throws IOException {
        ZooKeeper zooKeeper = new ZooKeeper(ip + ":" + port, 2000, new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println("[zk] 事件-" + event.getType() + "被触发");
            }
        });
        return zooKeeper;
    }
}
