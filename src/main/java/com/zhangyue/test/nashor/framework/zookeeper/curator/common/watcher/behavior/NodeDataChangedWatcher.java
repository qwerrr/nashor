package com.zhangyue.test.nashor.framework.zookeeper.curator.common.watcher.behavior;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import com.zhangyue.test.nashor.framework.zookeeper.curator.common.watcher.GetDataWatcher;
import com.zhangyue.test.nashor.framework.zookeeper.curator.common.ZkService;

/**
 * 节点数据改变监听器
 * @author YanMeng
 * @date 16-9-19
 */
public class NodeDataChangedWatcher extends GetDataWatcher{

    public NodeDataChangedWatcher(ZkService zkService, Watcher watcher, String path) {
        super(zkService, watcher, path);
    }

    @Override
    public void dataChangedCallBack(WatchedEvent event) {
        logger.debug("==NodeDataChangedWatcher - dataChangedCallBack==");
        getWatcher().process(event);
    }

    @Override
    public void deletedCallBack(WatchedEvent event) {
        logger.debug("==NodeDataChangedWatcher - deletedCallBack==");
        //NOTHING TO DO
        //当该方法执行时, 节点已经被删除, 不需要再次绑定事件
    }
}
