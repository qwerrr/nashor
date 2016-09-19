package com.zhangyue.test.nashor.framework.zookeeper.curator.config_service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhangyue.test.nashor.framework.zookeeper.curator.common.ZkContext;
import com.zhangyue.test.nashor.framework.zookeeper.curator.common.ZkService;

/**
 * 配置信息新增/删除监听器
 *
 * @author YanMeng
 * @date 16-9-18
 */
public class ConfAddOrDelWatcher implements Watcher {

    Logger logger = LoggerFactory.getLogger(ConfAddOrDelWatcher.class);

    ZkService zkService;
    Map<String, String> configs;
    String root;

    public ConfAddOrDelWatcher(ZkService zkService, Map<String, String> configs, String root) {
        this.zkService = zkService;
        this.configs = configs;
        this.root = root;
    }

    public void process(WatchedEvent event) {
        try {

            String path = event.getPath();

            Set<String> keys = configs.keySet();
            List<String> remoteKeys = zkService.getChildren(path);

            //远程key比本地多的需要增加
            Set<String> needAdd = new HashSet<String>();
            needAdd.addAll(remoteKeys);
            needAdd.removeAll(keys);
            for (String key : needAdd) {
                String value = new String(zkService.getData(path + ZkContext.PATH_SEPARATOR + key));
                logger.debug("[配置中心回调]新增 key - {}; value - {}", key, value);
                configs.put(key, value);
                zkService.ndcCallBack(event.getPath(), new ConfUpdateWatcher(this.zkService, this.configs, this.root));
            }

            //本地key比远程多的需要删除
            Set<String> needDel = new HashSet<String>();
            needDel.addAll(keys);
            needDel.removeAll(remoteKeys);
            for (String key : needDel) {
                logger.debug("[配置中心回调]删除 key - {}", key);
                configs.remove(key);
            }

            zkService.nccCallBack(path, new ConfAddOrDelWatcher(this.zkService, this.configs, this.root));

        } catch (Exception e) {
            logger.error("新增/删除配置数据异常:");
            e.printStackTrace();
        }
    }
}
