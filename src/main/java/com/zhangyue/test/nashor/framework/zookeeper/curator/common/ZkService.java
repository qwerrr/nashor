package com.zhangyue.test.nashor.framework.zookeeper.curator.common;

import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;

import com.zhangyue.test.nashor.framework.zookeeper.curator.common.watcher.behavior.*;

/**
 * Curator框架封装, 将和方法相关的watcher转为和事件相关
 *
 * @author YanMeng
 * @date 16-9-19
 */
public class ZkService {

    private CuratorFramework client;

    /**
     * @param zkConnect 格式: ip:port[,ip:port]...
     */
    public ZkService(String zkConnect){

        RetryPolicy policy = new ExponentialBackoffRetry(1000, Integer.MAX_VALUE);
        client = CuratorFrameworkFactory.builder().connectString(zkConnect)
            .sessionTimeoutMs(30000)
            .connectionTimeoutMs(30000)
            .canBeReadOnly(false)
            .retryPolicy(policy)
            .defaultData(null)
            .build();
        client.start();
    }

    public ZkService(String zkConnect, RetryPolicy policy){
        client = CuratorFrameworkFactory.builder().connectString(zkConnect)
            .sessionTimeoutMs(30000)
            .connectionTimeoutMs(30000)
            .canBeReadOnly(false)
            .retryPolicy(policy)
            .defaultData(null)
            .build();
        client.start();
    }

    //================================================================================ base option begin
    public boolean exists(String path) throws Exception {
        return client.checkExists().forPath(path) == null;
    }

    public boolean create(String path, byte[] data, CreateMode createMode) throws Exception {
        client.create().withMode(createMode).forPath(path, data);
        return Boolean.TRUE;
    }

    public List<String> getChildren(String path) throws Exception {
        return client.getChildren().forPath(path);
    }

    public byte[] getData(String path) throws Exception {
        return client.getData().forPath(path);
    }

    public void setData(String path, byte[] data) throws Exception {
        client.setData().forPath(path, data);
    }

    public void delete(String path) throws Exception{
        client.delete().forPath(path);
    }
    //================================================================================ base option end

    //================================================================================ extend option begin

    /**
     * 绑定NodeCreated回调事件, 节点创建时触发
     * ps:节点不存在时即可添加监听
     *
     * @param path
     * @param watcher
     */
    public void ncCallBack(String path, Watcher watcher) throws Exception {
        client.checkExists().usingWatcher(new NodeCreatedWatcher(this, watcher, path)).forPath(path);
    }

    /**
     * 绑定NodeDataChanged回调事件, 节点数据修改时触发
     * ps:节点不存在不可添加
     *
     * @param path
     * @param watcher
     */
    public void ndcCallBack(String path, Watcher watcher) throws Exception {
        client.checkExists().usingWatcher(new NodeDataChangedWatcher(this, watcher, path)).forPath(path);
    }

    /**
     * 绑定NodeDeleted回调事件, 节点被删除时触发
     * ps:节点不存在不可添加
     *
     * @param path
     * @param watcher
     * @throws Exception
     */
    public void ndCallBack(String path, Watcher watcher) throws Exception {
        client.checkExists().usingWatcher(new NodeDeletedWatcher(this, watcher, path)).forPath(path);
    }

    /**
     * 绑定NodeChildrenChanged回调事件, 节点的子节点创建/删除时触发
     * ps:节点不存在不可添加
     *
     * @param path
     * @param watcher
     * @throws Exception
     */
    public void nccCallBack(String path, Watcher watcher) throws Exception {
        client.checkExists().usingWatcher(new NodeChildrenChangedWatcher(this, watcher, path)).forPath(path);
    }

    /**
     * 绑定回调事件
     * @param path
     * @param watcher
     * @throws Exception
     */
    public void callBack(String path, Watcher watcher) throws Exception {
        client.checkExists().usingWatcher(watcher).forPath(path);
    }

    //================================================================================ extend option end

    public void destory(){
        client.close();
        client = null;
    }
}
