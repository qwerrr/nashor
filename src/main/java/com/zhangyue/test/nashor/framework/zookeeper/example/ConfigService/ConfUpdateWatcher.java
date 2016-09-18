package com.zhangyue.test.nashor.framework.zookeeper.example.ConfigService;

import org.apache.zookeeper.WatchedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhangyue.test.nashor.framework.zookeeper.common.GetDataWatcher;

/**
 * 配置信息修改监听器
 * @author YanMeng
 * @date 16-9-18
 */
public class ConfUpdateWatcher extends GetDataWatcher {

    Logger logger = LoggerFactory.getLogger(ConfUpdateWatcher.class);

    private String path;
    private ConfigServiceContext context;

    public ConfUpdateWatcher(ConfigServiceContext context, String path) {
        this.context = context;
        this.path = path;
    }

    @Override
    public void dataChangedCallBack(WatchedEvent event) {
        try {

            String key = path.replace(context.getRoot() + "/", "");
            String value = new String(context.getZkClient().getData(path, false, null));
            context.getConfigs().put(key, value);

            logger.debug("[配置中心回调]修改 key - {}; value - {}", key, value);

            context.getWatcherHolder().bindConfUpdateWatcher(path);
        } catch (Exception e) {
            logger.error("更新配置数据异常:");
            e.printStackTrace();
        }
    }

    @Override
    public void deletedCallBack(WatchedEvent event) {
        //NOTHING TO DO
    }
}
