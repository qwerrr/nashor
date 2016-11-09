package com.zhangyue.test.nashor.framework.zookeeper.common.curator.watcher.behavior;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import com.zhangyue.test.nashor.framework.zookeeper.common.curator.ZkCuratorService;
import com.zhangyue.test.nashor.framework.zookeeper.common.curator.watcher.ExistWatcher;

/**
 * 节点创建监听器
 * @author YanMeng
 * @date 16-9-19
 */
public class NodeCreatedWatcher extends ExistWatcher {

    public NodeCreatedWatcher(ZkCuratorService zkService, Watcher watcher, String path) {
        super(zkService, watcher, path);
    }

    @Override
    public void createCallBack(WatchedEvent event) {
        logger.debug("==NodeCreatedWatcher - createCallBack==");
        getWatcher().process(event);
    }

    @Override
    public void dataChangedCallBack(WatchedEvent event) {
        logger.debug("==NodeCreatedWatcher - dataChangedCallBack==");
        //NOTHING TO DO
    }

    @Override
    public void deletedCallBack(WatchedEvent event) {
        logger.debug("==NodeCreatedWatcher - deletedCallBack==");
        //NOTHING TO DO
    }
}
