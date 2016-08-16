package com.zhangyue.test.nashor.zookeeper.example;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import com.zhangyue.test.nashor.zookeeper.ZkCommon;

/**
 * 使用zookeeper实现统一的命名服务
 * @author YanMeng
 * @date 16-8-16
 */
public class NameService {

    private static final String DEFAULT_NAME_SERVICE_ROOT = "/NameService";
    private static final String NAME_SERVICE_VALUE = "ns";

    private String root;
    private ZooKeeper zooKeeper;

    private NameService(String root){
        this.root = (null == root || "".equals(root)) ? DEFAULT_NAME_SERVICE_ROOT : root;
    }

    /**
     * 创建NameService实例, 在获取实例时root路径已经被创建好了
     * @param root
     * @return
     * @throws IOException
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static NameService getInstance(String root) throws IOException, KeeperException, InterruptedException {
        NameService nameService = new NameService(root);
        nameService.init();
        return nameService;
    }

    private void init() throws KeeperException, InterruptedException, IOException {
        zooKeeper = ZkCommon.getZk();
        if(null == zooKeeper.exists(root, false)){
            zooKeeper.create(root, NAME_SERVICE_VALUE.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }

    /**
     * 创建命名队列
     * @param name
     * @return
     */
    public boolean queueNameing(String name) {
        try {
            String path = root.concat(name.startsWith("/") ? name : "/"+name);
            this.zooKeeper.create(path, NAME_SERVICE_VALUE.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT_SEQUENTIAL);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 创建唯一命名
     * @param name
     * @return 0 成功; 1 失败; -1 命名已存在;
     */
    public int nameing(String name) {
        int statCode = 0;

        try {
            String path = root.concat(name.startsWith("/") ? name : "/"+name);
            if(null == this.zooKeeper.exists(path, false)){
                this.zooKeeper.create(path, NAME_SERVICE_VALUE.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

        }catch (Exception e){
            e.printStackTrace();
            statCode = 1;
        }

        return statCode;
    }

    /**
     * 获取所有的命名
     * @return
     */
    public List<String> all(){
        List<String> nameings;
        try {
            nameings = this.zooKeeper.getChildren(root, false);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return nameings;
    }

    /**
     * 销毁对象, 并移除所有命名
     * 不应该被重复调用, 当然示例没有在代码中强制限制
     * @return
     */
    public boolean destory(){
        try {
            List<String> sonPathList = all();
            for (String sonPath : sonPathList){
                String path = this.root.concat("/").concat(sonPath);
                this.zooKeeper.delete(path, -1);
            }
            this.zooKeeper.delete(this.root, -1);
            this.zooKeeper.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 断开连接
     * 不应该被重复调用, 当然示例没有在代码中强制限制
     * @return
     */
    public boolean unConnect(){
        try {
            this.zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        NameService nameService = NameService.getInstance("/NameService1");
//        nameService.queueNameing("test");
//        nameService.nameing("/behaviorName");
        System.out.println(nameService.all());
        nameService.destory();

    }


}
