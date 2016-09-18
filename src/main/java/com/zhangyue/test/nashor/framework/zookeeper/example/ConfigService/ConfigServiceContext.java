package com.zhangyue.test.nashor.framework.zookeeper.example.ConfigService;

import java.util.Map;

import org.apache.zookeeper.ZooKeeper;

/**
 * 配置服务信息, 用于参数传递
 * @author YanMeng
 * @date 16-9-18
 */
class ConfigServiceContext {

    private static final String ZK_PATH_SEPARATOR = "/";

    //监听的管理者
    private ConfServiceWatcherHolder watcherHolder;
    //本地配置信息
    private Map<String, String> configs = null;
    //zk连接
    private ZooKeeper zkClient;
    //配置中心在zk上的根节点
    private String root;

    public ConfigServiceContext(Map<String, String> configs, ZooKeeper zkClient, String root) {
        this.configs = configs;
        this.zkClient = zkClient;
        this.root = root;
    }

    /**
     * 通过key获取配置在zk中的路径地址
     * @param key
     * @return
     */
    public String getPath(String key){
        return root + ZK_PATH_SEPARATOR + key;
    }

    public ConfServiceWatcherHolder getWatcherHolder() {
        return watcherHolder;
    }

    public void setWatcherHolder(ConfServiceWatcherHolder watcherHolder) {
        this.watcherHolder = watcherHolder;
    }

    public Map<String, String> getConfigs() {
        return configs;
    }

    public void setConfigs(Map<String, String> configs) {
        this.configs = configs;
    }

    public ZooKeeper getZkClient() {
        return zkClient;
    }

    public void setZkClient(ZooKeeper zkClient) {
        this.zkClient = zkClient;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }
}
