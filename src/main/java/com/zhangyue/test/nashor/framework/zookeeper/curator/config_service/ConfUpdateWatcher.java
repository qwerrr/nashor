package com.zhangyue.test.nashor.framework.zookeeper.curator.config_service;

import java.util.Map;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhangyue.test.nashor.framework.zookeeper.curator.common.ZkContext;
import com.zhangyue.test.nashor.framework.zookeeper.curator.common.ZkService;

/**
 * 配置信息修改监听器
 * @author YanMeng
 * @date 16-9-18
 */
public class ConfUpdateWatcher implements Watcher {

    Logger logger = LoggerFactory.getLogger(ConfUpdateWatcher.class);

    ZkService zkService;
    Map<String, String> configs;
    String root;

    public ConfUpdateWatcher(ZkService zkService, Map<String, String> configs, String root) {
        this.zkService = zkService;
        this.configs = configs;
        this.root = root;
    }

    public void process(WatchedEvent event) {
        try {
            String key = event.getPath().replace(root + ZkContext.PATH_SEPARATOR, "");
            String value = new String(zkService.getData(event.getPath()));
            configs.put(key, value);
            logger.debug("[配置中心回调]修改 key - {}; value - {}", key, value);
            zkService.ndcCallBack(event.getPath(), new ConfUpdateWatcher(this.zkService, this.configs, this.root));
        } catch (Exception e) {
            logger.error("更新配置数据异常:");
            e.printStackTrace();
        }
    }
}
