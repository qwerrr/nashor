package com.zhangyue.test.nashor.framework.zookeeper.curator.common.watcher.behavior;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import com.zhangyue.test.nashor.framework.zookeeper.curator.common.watcher.GetDataWatcher;
import com.zhangyue.test.nashor.framework.zookeeper.curator.common.ZkService;

/**
 * @author YanMeng
 * @date 16-9-19
 */
public class NodeDeletedWatcher extends GetDataWatcher{

    public NodeDeletedWatcher(ZkService zkService, Watcher watcher, String path) {
        super(zkService, watcher, path);
    }

    @Override
    public void dataChangedCallBack(WatchedEvent event) {
        try {
            getZkService().ndCallBack(getPath(), getWatcher());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletedCallBack(WatchedEvent event) {
        getWatcher().process(event);
    }
}
