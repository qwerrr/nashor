package com.zhangyue.test.nashor.framework.zookeeper.curator.common.watcher.behavior;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import com.zhangyue.test.nashor.framework.zookeeper.curator.common.watcher.GetDataWatcher;
import com.zhangyue.test.nashor.framework.zookeeper.curator.common.ZkService;

/**
 * @author YanMeng
 * @date 16-9-19
 */
public class NodeDataChangedWatcher extends GetDataWatcher{

    public NodeDataChangedWatcher(ZkService zkService, Watcher watcher, String path) {
        super(zkService, watcher, path);
    }

    @Override
    public void dataChangedCallBack(WatchedEvent event) {
        getWatcher().process(event);
    }

    @Override
    public void deletedCallBack(WatchedEvent event) {
        try {
            getZkService().ndcCallBack(getPath(), getWatcher());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
