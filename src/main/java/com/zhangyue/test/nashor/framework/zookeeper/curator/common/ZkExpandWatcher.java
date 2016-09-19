package com.zhangyue.test.nashor.framework.zookeeper.curator.common;

import org.apache.zookeeper.Watcher;

/**
 * @author YanMeng
 * @date 16-9-19
 */
public class ZkExpandWatcher {

    private ZkService zkService;
    private Watcher watcher;
    private String path;

    public ZkExpandWatcher(){}
    public ZkExpandWatcher(ZkService zkService, Watcher watcher, String path){
        this.zkService = zkService;
        this.watcher = watcher;
        this.path = path;
    }

    protected ZkService getZkService() {
        if(watcher == null)
            throw new NullPointerException("getZkService-zkService为空");
        return zkService;
    }

    protected Watcher getWatcher() {
        if(watcher == null)
            throw new NullPointerException("getWatcher-watcher为空");
        return watcher;
    }

    protected String getPath() {
        if(path == null)
            throw new NullPointerException("getPath-path为空");
        return path;
    }
}
