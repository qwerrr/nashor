package com.zhangyue.test.nashor.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * zookeeper watcher测试
 * @author YanMeng
 * @date 16-8-9
 */
public class ZookeeperWatcherTest {

    static Logger logger = LoggerFactory.getLogger(ZookeeperWatcherTest.class);

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        ZooKeeper zooKeeper = ZkCommon.getZk();

        String path = "/rootTest";
        logger.debug("begin 创建根节点:{}",path);
        zooKeeper.create(path, "测试根节点数据".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        logger.debug("end   创建根节点:{}",path);

        setWatcher(zooKeeper);
        triggerWatcher(zooKeeper);

        logger.debug("begin 修改根节点数据");
        zooKeeper.setData(path, "修改根节点数据".getBytes(), -1);
        logger.debug("end   修改根节点数据");

        logger.debug("begin 删除根节点:{}",path);
        zooKeeper.delete(path, -1);
        logger.debug("end   删除根节点:{}",path);
    }

    /**
     * 查询类型的
     * 可以设置观察的操作：exists,getChildren,getData
     */
    private static void setWatcher(ZooKeeper zooKeeper) throws KeeperException, InterruptedException {
        logger.debug("设置exists方法Watcher");
        zooKeeper.exists("/rootTest/sonTest", new Watcher() {
            public void process(WatchedEvent event) {
                logger.debug("->exists方法Watcher被触发:{}", event.getType());
            }
        });
        logger.debug("设置getChildren方法Watcher");
        zooKeeper.getChildren("/rootTest", new Watcher() {
            public void process(WatchedEvent event) {
                logger.debug("->getChildren方法Watcher被触发:{}", event.getType());
            }
        });
        logger.debug("设置getData方法Watcher");
        zooKeeper.getData("/rootTest", new Watcher() {
            public void process(WatchedEvent event) {
                logger.debug("->getData方法Watcher被触发:{}", event.getType());
            }
        },null);

        zooKeeper.getData("/rootTest", true,null);
    }

    /**
     * 可以触发观察的操作：create,delete,setData
     */
    private static void triggerWatcher(ZooKeeper zooKeeper) throws KeeperException, InterruptedException {
        String path = "/rootTest/sonTest";

        logger.debug("begin 创建子节点:{}", path);
        zooKeeper.create(path, "修改子节点数据".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        logger.debug("end   创建子节点:{}", path);

        zooKeeper.exists("/rootTest/sonTest", new Watcher() {
            public void process(WatchedEvent event) {
                logger.debug("->exists方法Watcher被触发:{}", event.getType());
            }
        });

        logger.debug("begin 修改子节点数据");
        zooKeeper.setData(path, "修改子节点数据".getBytes(), -1);
        logger.debug("end   修改子节点数据");

        logger.debug("begin 删除子节点:{}", path);
        zooKeeper.delete(path, -1);
        logger.debug("end   删除子节点:{}", path);
    }
}
