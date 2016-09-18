package com.zhangyue.test.nashor.framework.zookeeper.example.ConfigService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.zookeeper.WatchedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhangyue.test.nashor.framework.zookeeper.common.GetChildrenWatcher;

/**
 * 配置信息新增/删除监听器
 * @author YanMeng
 * @date 16-9-18
 */
public class ConfAddOrDelWatcher extends GetChildrenWatcher {

    Logger logger = LoggerFactory.getLogger(ConfAddOrDelWatcher.class);

    private ConfigServiceContext context;

    public ConfAddOrDelWatcher(ConfigServiceContext context) {
        this.context = context;
    }

    @Override
    public void childrenChangedCallBack(WatchedEvent event) {
        try {

            Set<String> keys = context.getConfigs().keySet();

            List<String> remoteKeys = context.getZkClient().getChildren(context.getRoot(), false);

            //远程key比本地多的需要增加
            Set<String> needAdd = new HashSet<String>();
            needAdd.addAll(remoteKeys);
            needAdd.removeAll(keys);
            for (String key : needAdd) {
                String path = context.getPath(key);
                String value = new String(context.getZkClient().getData(path, false, null));
                logger.debug("[配置中心回调]新增 key - {}; value - {}", key, value);
                context.getConfigs().put(key, value);
                context.getWatcherHolder().bindConfUpdateWatcher(path);
            }

            //本地key比远程多的需要删除
            Set<String> needDel = new HashSet<String>();
            needDel.addAll(keys);
            needDel.removeAll(remoteKeys);
            for (String key : needDel) {
                logger.debug("[配置中心回调]删除 key - {}", key);
                context.getConfigs().remove(key);
            }

            context.getWatcherHolder().bindConfAddOrDelWatcher();

        } catch (Exception e) {
            logger.error("新增/删除配置数据异常:");
            e.printStackTrace();
        }
    }

    @Override
    public void deletedCallBack(WatchedEvent event) {
        //NOTHING TO DO
    }
}
