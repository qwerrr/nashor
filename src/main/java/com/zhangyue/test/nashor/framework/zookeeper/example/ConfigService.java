package com.zhangyue.test.nashor.framework.zookeeper.example;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import com.zhangyue.test.nashor.framework.zookeeper.ZkConnFactory;

/**
 * ZK实现配置管理
 * TODO 扩展配置分组? 真的需要吗?
 *
 * @author YanMeng
 * @date 16-9-2
 */
public class ConfigService {

    private static final String DEFAULT_CONFIG_SERVICE_ROOT = "/ConfigService";

    private static String root = DEFAULT_CONFIG_SERVICE_ROOT;
    private static boolean inited = false;

    private static ZooKeeper zkClient;

    private static ConcurrentHashMap<String, String> configs = new ConcurrentHashMap<String, String>();

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

        initZkEnvironment(ip, port);

        ConfigService.inited = Boolean.TRUE;
        return ConfigService.inited;
    }

    private static void initZkEnvironment(String ip, int port) throws ZkException {

        //获取连接
        try {
            zkClient = ZkConnFactory.getZk(ip, port);
        } catch (IOException e) {
            throw new ZkException("[zk配置管理]连接失败, 请检查网络连通性 - " + ip + ":" + port);
        }

        //如果根节点不存在创建根节点, 如果存在, 加载配置到内存
        try {
            if(zkClient.exists(DEFAULT_CONFIG_SERVICE_ROOT, false) == null){
                zkClient.create(DEFAULT_CONFIG_SERVICE_ROOT, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }else{
                loadConfigs(zkClient);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ZkException("[zk配置管理]初始化zk环境失败");
        }
    }

    private static void loadConfigs(ZooKeeper zkClient) throws KeeperException, InterruptedException {
        List<String> childrens = zkClient.getChildren(root, false);
        for(String children : childrens){
            String value = new String(zkClient.getData(children, false, null));
            configs.put(value.replace(root + "/", ""), value);

            //TODO bind watcher监听配置修改

        }

    }


    public static boolean init(String ip, int port, String root) throws IOException, KeeperException, InterruptedException {
        if(init(ip, port)){
            ConfigService.root = root;
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 设置配置信息, 如果已经存在, 不进行更新
     * @param key
     * @param value
     * @return
     */
    public boolean setConfig(String key, String value) throws KeeperException, InterruptedException {
        String path = root.concat(key.startsWith("/") ? key : "/").concat(key);
        if(this.zkClient.exists(path, false) == null){
            this.zkClient.create(path, value.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
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
        String path = root.concat(key.startsWith("/") ? key : "/").concat(key);
        if(this.zkClient.exists(path, false) != null){
            this.zkClient.setData(path, value.getBytes(), -1);
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

        String path = root.concat(key.startsWith("/") ? key : "/").concat(key);

        if(this.zkClient.exists(path, false) == null){
            this.zkClient.create(path, value.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }else{
            this.zkClient.setData(path, value.getBytes(), -1);
        }
        return Boolean.TRUE;
    }



    public static void main(String[] args) {

    }
}

