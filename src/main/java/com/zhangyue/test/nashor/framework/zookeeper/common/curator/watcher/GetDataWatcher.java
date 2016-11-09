package com.zhangyue.test.nashor.framework.zookeeper.common.curator.watcher;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import com.zhangyue.test.nashor.framework.zookeeper.common.curator.ZkCuratorService;
import com.zhangyue.test.nashor.framework.zookeeper.common.curator.ZkExpandWatcher;

/**
 * getData绑定watcher, 扩展原有watcher接口, 将枚举转为不同方法回调
 *
 * getData所绑定的watcher会被以下事件所触发:
 *      NodeDataChanged     节点数据被修改
 *      NodeDeleted         节点被删除
 *
 * @author YanMeng
 * @date 16-9-14
 */
public abstract class GetDataWatcher extends ZkExpandWatcher implements Watcher{

    public GetDataWatcher() {
    }

    public GetDataWatcher(ZkCuratorService zkService, Watcher watcher, String path) {
        super(zkService, watcher, path);
    }


    public void process(WatchedEvent event) {

        Event.EventType eventType = event.getType();

        switch (eventType){
            case NodeDataChanged:
                dataChangedCallBack(event); break;
            case NodeDeleted:
                deletedCallBack(event); break;
            default:
                unKnownEventType(event);
        }
    }

    public abstract void dataChangedCallBack(WatchedEvent event);

    public abstract void deletedCallBack(WatchedEvent event);

    /**
     * 如果需要可以重写
     */
    public void unKnownEventType(WatchedEvent event){
        //NOTHING TO DO
    }
}
