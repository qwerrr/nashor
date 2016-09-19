package com.zhangyue.test.nashor.framework.zookeeper.curator.common.watcher;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import com.zhangyue.test.nashor.framework.zookeeper.curator.common.ZkExpandWatcher;
import com.zhangyue.test.nashor.framework.zookeeper.curator.common.ZkService;

/**
 * getChildren绑定watcher, 扩展原有watcher接口, 将枚举转为不同方法回调
 *
 * getChildren所绑定的watcher会被以下事件所触发:
 *      NodeChildrenChanged 子节点被创建/删除
 *      NodeDeleted         '当前'节点被删除
 *
 * @author YanMeng
 * @date 16-9-14
 */
public abstract class GetChildrenWatcher extends ZkExpandWatcher implements Watcher {

    public GetChildrenWatcher() {
    }

    public GetChildrenWatcher(ZkService zkService, Watcher watcher, String path) {
        super(zkService, watcher, path);
    }


    public void process(WatchedEvent event) {

        Event.EventType eventType = event.getType();

        switch (eventType){
            //子节点创建和删除时都会触发 NodeChildrenChanged
            case NodeChildrenChanged:
                childrenChangedCallBack(event); break;
            case NodeDeleted:
                deletedCallBack(event); break;
            default:
                unKnownEventType(event);
        }
    }

    public abstract void childrenChangedCallBack(WatchedEvent event);

    public abstract void deletedCallBack(WatchedEvent event);

    /**
     * 如果需要可以重写
     */
    public void unKnownEventType(WatchedEvent event){
        //NOTHING TO DO
    }
}
