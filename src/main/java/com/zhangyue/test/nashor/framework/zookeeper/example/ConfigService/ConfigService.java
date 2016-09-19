package com.zhangyue.test.nashor.framework.zookeeper.example.ConfigService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhangyue.test.nashor.framework.zookeeper.ZkConnFactory;

/**
 * ZK实现配置管理
 * TODO 扩展配置分组? 真的需要吗?
 *
 * @author YanMeng
 * @date 16-9-2
 */
public class ConfigService {

    private static Logger logger = LoggerFactory.getLogger(ConfigService.class);

    private static final String DEFAULT_CONFIG_SERVICE_ROOT = "/ZkConfigService";

    private boolean inited = false;

    //本地配置信息
    private volatile Map<String, String> configs = new HashMap<String, String>();
    //监听管理者
    private ConfServiceWatcherHolder watcherHolder;
    //zk连接
    private ZooKeeper zkClient;
    //配置中心根节点
    private String root = DEFAULT_CONFIG_SERVICE_ROOT;

    /**
     * 初始化
     * @param ip
     * @param port
     * @return
     * @throws IOException
     * @throws KeeperException
     * @throws InterruptedException
     */
    public boolean init(String ip, int port) throws IOException, KeeperException, InterruptedException {

        logger.info("[初始化配置中心]开始");

        //0. 校验是否已经初始化
        if(inited){
            logger.info("[初始化配置中心]配置中心已经被初始化, 提前退出");
            return Boolean.FALSE;
        }

        //1. 获取连接
        try {
            zkClient = ZkConnFactory.getZk(ip, port);
        } catch (IOException e) {
            logger.error("[初始化配置中心]zk连接失败, 请检查网络连接 - {}:{} ", ip, port);
            return Boolean.FALSE;
        }

        //2. 初始化watcher的管理器
        ConfigServiceContext context = new ConfigServiceContext(configs, zkClient, root);
        watcherHolder = new ConfServiceWatcherHolder(context);

        //3. 创建根目录, 加载数据到本地, 并绑定配置内容修改监听
        try {
            if(zkClient.exists(root, false) == null){
                zkClient.create(root, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

            List<String> keys = zkClient.getChildren(root, false);
            for(String key : keys){
                String path = root + '/' + key;
                String value = new String(zkClient.getData(path, false, null));
                configs.put(key, value);
                watcherHolder.bindConfUpdateWatcher(path);
            }
        } catch (Exception e) {
            logger.error("[初始化配置中心]加载配置失败:");
            e.printStackTrace();
            configs.clear();
            return Boolean.FALSE;
        }

        //4.绑定配置新增/删除监听
        watcherHolder.bindConfAddOrDelWatcher();

        inited = Boolean.TRUE;

        logger.info("[初始化配置中心]结束");
        return Boolean.TRUE;
    }


    public String getConfig(String key){
        return configs.get(key);
    }

    public Map<String, String> getConfigs(){
        Map<String, String> map = new HashMap<String, String>();
        map.putAll(configs);
        return map;
    }

    /**
     * 设置配置信息, 如果已经存在, 不进行更新
     * @param key
     * @param value
     * @return
     */
    public boolean saveConfig(String key, String value) throws KeeperException, InterruptedException {

        configs.put(key, value);

        String path = root.concat(key.startsWith("/") ? key : "/").concat(key);
        if(zkClient.exists(path, false) == null){
            zkClient.create(path, value.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            watcherHolder.bindConfUpdateWatcher(path);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 更新配置信息, 如果不存在, 不进行创建
     * @param key
     * @param value
     * @return
     */
    public boolean updateConfig(String key, String value) throws KeeperException, InterruptedException {

        configs.put(key, value);

        String path = root.concat(key.startsWith("/") ? key : "/").concat(key);
        if(zkClient.exists(path, false) != null){
            zkClient.setData(path, value.getBytes(), -1);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    /**
     * 设置或更新配置信息
     * @param key
     * @param value
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public boolean saveOrUpdateConfig(String key, String value) throws KeeperException, InterruptedException {

        configs.put(key, value);

        String path = root.concat(key.startsWith("/") ? key : "/").concat(key);

        if(zkClient.exists(path, false) == null){
            zkClient.create(path, value.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            watcherHolder.bindConfUpdateWatcher(path);
        }else{
            zkClient.setData(path, value.getBytes(), -1);
        }
        return Boolean.TRUE;
    }

    public boolean removeConfig(String key) throws KeeperException, InterruptedException {
        configs.remove(key);

        String path = root.concat(key.startsWith("/") ? key : "/").concat(key);
        if(zkClient.exists(path, false) != null){
            zkClient.delete(path, -1);
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }

}

