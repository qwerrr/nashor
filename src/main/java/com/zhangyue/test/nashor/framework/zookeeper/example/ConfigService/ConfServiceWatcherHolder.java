package com.zhangyue.test.nashor.framework.zookeeper.example.ConfigService;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;

/**
 *
 * @author YanMeng
 * @date 16-9-18
 */
public class ConfServiceWatcherHolder {

    private ConfigServiceContext context;

    public ConfServiceWatcherHolder(ConfigServiceContext context) {
        this.context = context;
        context.setWatcherHolder(this);
    }

    /**
     * 绑定配置修改watcher
     */
    void bindConfUpdateWatcher(String path) throws KeeperException, InterruptedException {
        Watcher watcher = new ConfUpdateWatcher(context, path);
        context.getZkClient().getData(path, watcher, null);
    }

    /**
     * 绑定配置新增/删除watcher
     */
    void bindConfAddOrDelWatcher() throws KeeperException, InterruptedException {
        Watcher watcher = new ConfAddOrDelWatcher(context);
        context.getZkClient().getChildren(context.getRoot(), watcher, null);
    }
}
