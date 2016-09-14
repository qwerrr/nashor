package com.zhangyue.test.nashor.framework.zookeeper.example.ConfigService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhangyue.test.nashor.framework.zookeeper.ZkConnFactory;
import com.zhangyue.test.nashor.framework.zookeeper.common.GetChildrenWatcher;
import com.zhangyue.test.nashor.framework.zookeeper.common.GetDataWatcher;
import com.zhangyue.test.nashor.framework.zookeeper.common.ZkException;

/**
 * ZK实现配置管理
 * TODO 扩展配置分组? 真的需要吗?
 *
 * @author YanMeng
 * @date 16-9-2
 */
public class ConfigService {

    private static Logger logger = LoggerFactory.getLogger(ConfigService.class);

    private static final String DEFAULT_CONFIG_SERVICE_ROOT = "/ConfigService";

    private static String root = DEFAULT_CONFIG_SERVICE_ROOT;
    private static boolean inited = false;

    private static ZooKeeper zkClient;

    private static volatile Map<String, String> configs = new HashMap<String, String>();


    //*****************************************************初始化 begin
    /**
     * 初始化
     * @param ip
     * @param port
     * @return
     * @throws IOException
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static boolean init(String ip, int port) throws IOException, KeeperException, InterruptedException {
        if(ConfigService.inited){
            return Boolean.FALSE;
        }

        try {
            initZkEnvironment(ip, port);
        } catch (ZkException e) {
            e.printStackTrace();
            configs.clear();
            return Boolean.FALSE;
        }

        ConfigService.inited = Boolean.TRUE;
        return Boolean.TRUE;
    }

    private static void initZkEnvironment(String ip, int port) throws ZkException {

        //获取连接
        try {
            zkClient = ZkConnFactory.getZk(ip, port);
        } catch (IOException e) {
            throw new ZkException("[zk配置管理]连接失败, 请检查网络连通性 - " + ip + ":" + port);
        }

        //如果根节点不存在创建根节点, 如果存在, 加载配置到内存并绑定watcher
        try {
            if(zkClient.exists(DEFAULT_CONFIG_SERVICE_ROOT, false) == null){
                zkClient.create(DEFAULT_CONFIG_SERVICE_ROOT, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }else{
                List<String> keys = zkClient.getChildren(root, false);
                for(String key : keys){

                    String path = root + '/' + key;
                    String value = new String(zkClient.getData(path, false, null));

                    configs.put(key, value);

                    bindConfUpdateWatcher(path);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ZkException("[zk配置管理]初始化zk环境失败");
        }

        //绑定根节点的watcher
        try {
            bindConfAddOrDelWatcher();
        } catch (Exception e) {
            throw new ZkException("[zk配置管理]初始化zk监听器失败");
        }
    }

    /**
     * 绑定配置修改watcher
     */
    private static void bindConfUpdateWatcher(String path) throws KeeperException, InterruptedException {

        Watcher watcher = new ConfUpdateWatcher(path);
        zkClient.getData(path, watcher, null);
    }

    private static void bindConfAddOrDelWatcher() throws KeeperException, InterruptedException {

        Watcher watcher = new ConfAddOrDelWatcher();
        zkClient.getChildren(root, watcher, null);
    }

    //*****************************************************初始化方法 end



    //*****************************************************操作配置方法 begin
    /**
     * 设置配置信息, 如果已经存在, 不进行更新
     * @param key
     * @param value
     * @return
     */
    public static boolean setConfig(String key, String value) throws KeeperException, InterruptedException {
        String path = root.concat(key.startsWith("/") ? key : "/").concat(key);
        if(zkClient.exists(path, false) == null){
            zkClient.create(path, value.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
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
    public static boolean updateConfig(String key, String value) throws KeeperException, InterruptedException {
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
    public static boolean saveOrUpdateConfig(String key, String value) throws KeeperException, InterruptedException {

        String path = root.concat(key.startsWith("/") ? key : "/").concat(key);

        if(zkClient.exists(path, false) == null){
            zkClient.create(path, value.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }else{
            zkClient.setData(path, value.getBytes(), -1);
        }
        return Boolean.TRUE;
    }
    //*****************************************************操作配置方法 end


    //*****************************************************内部类, 实现watcher begin

    static class ConfUpdateWatcher extends GetDataWatcher{

        String path;
        public ConfUpdateWatcher(String path){
            this.path = path;
        }

        @Override
        public void dataChangedCallBack(WatchedEvent event) {
            try {
                String value = new String(zkClient.getData(path, false, null));
                configs.put(path.replace(root + "/", ""), value);
                bindConfUpdateWatcher(path);
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

    static class ConfAddOrDelWatcher extends GetChildrenWatcher{

        @Override
        public void childrenChangedCallBack(WatchedEvent event) {
            try {
                String path = event.getPath();
                String key = path.replace(root + '/', "");
                //如果已经存在的节点使用create再次创建时无法触发该watcher即可成立
                if(configs.containsKey(key)){
                    configs.remove(key);
                }else{
                    String value = new String(zkClient.getData(path, false, null));
                    configs.put(key, value);
                }
                bindConfAddOrDelWatcher();
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

    //*****************************************************内部类, 实现watcher end
}

