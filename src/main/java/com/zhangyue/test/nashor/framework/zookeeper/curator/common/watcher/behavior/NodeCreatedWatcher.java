package com.zhangyue.test.nashor.framework.zookeeper.curator.common.watcher.behavior;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import com.zhangyue.test.nashor.framework.zookeeper.curator.common.watcher.ExistWatcher;
import com.zhangyue.test.nashor.framework.zookeeper.curator.common.ZkService;

/**
 *
 * @author YanMeng
 * @date 16-9-19
 */
public class NodeCreatedWatcher extends ExistWatcher{

    public NodeCreatedWatcher(ZkService zkService, Watcher watcher, String path) {
        super(zkService, watcher, path);
    }

    @Override
    public void createCallBack(WatchedEvent event) {
        getWatcher().process(event);
    }

    @Override
    public void dataChangedCallBack(WatchedEvent event) {
        try {
            getZkService().ncCallBack(getPath(), getWatcher());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletedCallBack(WatchedEvent event) {
        try {
            getZkService().ncCallBack(getPath(), getWatcher());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
