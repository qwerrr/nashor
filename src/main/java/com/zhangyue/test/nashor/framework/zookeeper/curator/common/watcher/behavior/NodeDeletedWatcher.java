package com.zhangyue.test.nashor.framework.zookeeper.curator.common.watcher.behavior;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import com.zhangyue.test.nashor.framework.zookeeper.curator.common.watcher.GetDataWatcher;
import com.zhangyue.test.nashor.framework.zookeeper.curator.common.ZkService;

/**
 * 节点删除监听器
 *
 * @author YanMeng
 * @date 16-9-19
 */
public class NodeDeletedWatcher extends GetDataWatcher{

    public NodeDeletedWatcher(ZkService zkService, Watcher watcher, String path) {
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
