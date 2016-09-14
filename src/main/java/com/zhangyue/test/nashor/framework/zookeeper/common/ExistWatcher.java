package com.zhangyue.test.nashor.framework.zookeeper.common;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;


/**
 * exist绑定watcher, 扩展原有watcher接口, 将枚举转为不同方法回调
 * @author YanMeng
 * @date 16-9-14
 */
public abstract class ExistWatcher implements Watcher{

    public void process(WatchedEvent event) {
        Event.EventType eventType = event.getType();

        switch (eventType){
            case NodeCreated:
                createCallBack(event); break;
            case NodeDataChanged:
                dataChangedCallBack(event); break;
            case NodeDeleted:
                deletedCallBack(event); break;
            default:
                unKnownEventType(event);
        }
    }

    public abstract void createCallBack(WatchedEvent event);

    public abstract void dataChangedCallBack(WatchedEvent event);

    public abstract void deletedCallBack(WatchedEvent event);

    /**
     * 如果需要可以重写
     */
    public void unKnownEventType(WatchedEvent event){
        //NOTHING TO DO
    }
}