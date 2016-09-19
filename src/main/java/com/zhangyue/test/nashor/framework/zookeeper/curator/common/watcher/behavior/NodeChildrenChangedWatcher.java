package com.zhangyue.test.nashor.framework.zookeeper.curator.common.watcher.behavior;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import com.zhangyue.test.nashor.framework.zookeeper.curator.common.watcher.GetChildrenWatcher;

import com.zhangyue.test.nashor.framework.zookeeper.curator.common.ZkService;

/**
 * 节点的子节点修改(新增/删除)监听器
 * @author YanMeng
 * @date 16-9-19
 */
public class NodeChildrenChangedWatcher extends GetChildrenWatcher{

    public NodeChildrenChangedWatcher(ZkService zkService, Watcher watcher, String path) {
        super(zkService, watcher, path);
    }

    @Override
    public void childrenChangedCallBack(WatchedEvent event) {
        logger.debug("==NodeChildrenChangedWatcher - childrenChangedCallBack==");
        getWatcher().process(event);
    }

    @Override
    public void deletedCallBack(WatchedEvent event) {
        logger.debug("==NodeChildrenChangedWatcher - deletedCallBack==");
        //NOTHING TO DO
        //当该方法执行时, 节点已经被删除, 不需要再次绑定事件
    }
}
