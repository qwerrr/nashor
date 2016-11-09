package com.zhangyue.test.nashor.framework.zookeeper.example.config;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhangyue.test.nashor.SeparatorConstant;
import com.zhangyue.test.nashor.framework.zookeeper.common.curator.ZkCuratorService;

/**
 * 配置信息新增/删除监听器
 *
 * @author YanMeng
 * @date 16-9-18
 */
public class ConfAddOrDelWatcher implements Watcher {

    Logger logger = LoggerFactory.getLogger(ConfAddOrDelWatcher.class);

    ZkCuratorService zkService;
    Map<String, String> configs;
    String root;

    public ConfAddOrDelWatcher(ZkCuratorService zkService, Map<String, String> configs, String root) {
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
                String value = new String(zkService.getData(path + SeparatorConstant.OBLIQUE + key));
                logger.debug("[配置中心回调]新增 key - {}; value - {}", key, value);
                configs.put(key, value);

                //新增配置绑定节点数据改变事件
                zkService.nodeDataChangedCallBack(
                        path + SeparatorConstant.OBLIQUE + key,
                        new ConfUpdateWatcher(this.zkService, this.configs, this.root)
                );
            }

            //本地key比远程多的需要删除
            Set<String> needDel = new HashSet<String>();
            needDel.addAll(keys);
            needDel.removeAll(remoteKeys);
            for (String key : needDel) {
                logger.debug("[配置中心回调]删除 key - {}", key);
                configs.remove(key);
            }

            //再次绑定子节点改变(新增/删除)事件
            zkService.nodeChildrenChangedCallBack(
                    path,
                    new ConfAddOrDelWatcher(this.zkService, this.configs, this.root)
            );

        } catch (Exception e) {
            logger.error("新增/删除配置数据异常:");
            e.printStackTrace();
        }
    }
}
