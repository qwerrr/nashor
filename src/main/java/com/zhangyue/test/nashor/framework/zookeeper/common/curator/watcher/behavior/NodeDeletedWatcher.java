package com.zhangyue.test.nashor.framework.zookeeper.common.curator.watcher.behavior;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import com.zhangyue.test.nashor.framework.zookeeper.common.curator.ZkCuratorService;
import com.zhangyue.test.nashor.framework.zookeeper.common.curator.watcher.GetDataWatcher;

/**
 * 节点删除监听器
 *
 * @author YanMeng
 * @date 16-9-19
 */
public class NodeDeletedWatcher extends GetDataWatcher {

    public NodeDeletedWatcher(ZkCuratorService zkService, Watcher watcher, String path) {
        super(zkService, watcher, path);
    }

    @Override
    public void dataChangedCallBack(WatchedEvent event) {
        logger.debug("==NodeDeletedWatcher - dataChangedCallBack==");
        try {
            getZkService().nodeDeletedCallBack(getPath(), getWatcher());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletedCallBack(WatchedEvent event) {
        logger.debug("==NodeDeletedWatcher - deletedCallBack==");
        getWatcher().process(event);
    }
}
