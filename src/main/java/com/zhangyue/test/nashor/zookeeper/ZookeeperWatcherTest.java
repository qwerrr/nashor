package com.zhangyue.test.nashor.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * zookeeper watcher测试
 * exists/getChildren/getData可将总watcher或者其他watcher绑定到某个节点
 * create/setData/delete可将某个节点上绑定的watcher按照不同绑定方式触发, 并且仅触发一次!
 * watcher在操作执行完成后被回调
 * 一个节点可以绑定多个watcher
 * 多个watcher被回调时顺序随机
 * 如果一个节点上的watcher直到删除都没有被触发, 删除后会将所有watcher均触发
 * 除了exists,其他两个绑定watcher的操作只有节点存在时可以执行
 *
 * exists - 1.节点创建时(NodeCreated); 2.节点数据被修改时(NodeDataChanged);
 * getChildren - 1.子节点创建时(NodeChildrenChanged); 2.子节点删除时(NodeChildrenChanged);
 * getData - 1.节点数据被修改时(NodeDataChanged);
 * ALL - 节点被删除时(NodeDeleted)
 *
 * 设置/修改类型的
 * 可以触发观察的操作：create,setData,delete
 *
 * @author YanMeng
 * @date 16-8-9
 */
public class ZookeeperWatcherTest {

    static Logger logger = LoggerFactory.getLogger(ZookeeperWatcherTest.class);

    static ZooKeeper zooKeeper = null;

    static String path = "/rootTest";
    static String path_son = "/rootTest/sonTest";

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        zooKeeper = ZkCommon.getZk();

//        existToRoot();
//        existToSon();

//        getChildrenToRoot();
//        getChildrenToSon();

//        getDataToRoot();
        getDataToSon();
    }


    /************************************************exists begin***********************************************/
    /**
     * 测试exists方法所绑定的watcher对于自身节点是否响应
     * @throws KeeperException
     * @throws InterruptedException
     */
    private static void existToRoot() throws KeeperException, InterruptedException {

        bindToNode(BindBehavior.exists, path);      //!!!

        logger.debug("[begin] 创建根节点:{}",path);
        zooKeeper.create(path, "测试根节点数据".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        logger.debug("[end]   创建根节点:{}",path);

        bindToNode(BindBehavior.exists, path);      //!!!

        logger.debug("[begin] 修改根节点数据");
        zooKeeper.setData(path, "修改根节点数据".getBytes(), -1);
        logger.debug("[end]   修改根节点数据");

        bindToNode(BindBehavior.exists, path);      //!!!

        logger.debug("[begin] 删除根节点:{}",path);
        zooKeeper.delete(path, -1);
        logger.debug("[end]   删除根节点:{}",path);
    }

    /**
     * 测试exists方法所绑定的watcher对于子节点是否响应
     * @throws KeeperException
     * @throws InterruptedException
     */
    private static void existToSon() throws KeeperException, InterruptedException {
        logger.debug("[begin] 创建根节点:{}",path);
        zooKeeper.create(path, "测试根节点数据".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        logger.debug("[end]   创建根节点:{}",path);

        logger.debug("[begin] 修改根节点数据");
        zooKeeper.setData(path, "修改根节点数据".getBytes(), -1);
        logger.debug("[end]   修改根节点数据");

        bindToNode(BindBehavior.exists, path);          //!!!

        logger.debug("[begin] 创建子节点:{}", path_son);
        zooKeeper.create(path_son, "修改子节点数据".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        logger.debug("[end]   创建子节点:{}", path_son);

        bindToNode(BindBehavior.exists, path);          //!!!

        logger.debug("[begin] 修改子节点数据");
        zooKeeper.setData(path_son, "修改子节点数据".getBytes(), -1);
        logger.debug("[end]   修改子节点数据");

        bindToNode(BindBehavior.exists, path);          //!!!

        logger.debug("[begin] 删除子节点:{}", path_son);
        zooKeeper.delete(path_son, -1);
        logger.debug("[end]   删除子节点:{}", path_son);

        logger.debug("[begin] 删除根节点:{}",path);
        zooKeeper.delete(path, -1);
        logger.debug("[end]   删除根节点:{}",path);
    }
    /************************************************exists end***********************************************/

    /************************************************getChildren begin***********************************************/
    /**
     * 测试getChildren方法所绑定的watcher对于自身节点是否响应
     * @throws KeeperException
     * @throws InterruptedException
     */
    private static void getChildrenToRoot() throws KeeperException, InterruptedException {

        logger.debug("[begin] 创建根节点:{}",path);
        zooKeeper.create(path, "测试根节点数据".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        logger.debug("[end]   创建根节点:{}",path);

        bindToNode(BindBehavior.getChildren, path);      //!!!

        logger.debug("[begin] 修改根节点数据");
        zooKeeper.setData(path, "修改根节点数据".getBytes(), -1);
        logger.debug("[end]   修改根节点数据");

        bindToNode(BindBehavior.getChildren, path);      //!!!

        logger.debug("[begin] 删除根节点:{}",path);
        zooKeeper.delete(path, -1);
        logger.debug("[end]   删除根节点:{}",path);
    }

    /**
     * 测试getChildren方法所绑定的watcher对于子节点是否响应
     * @throws KeeperException
     * @throws InterruptedException
     */
    private static void getChildrenToSon() throws KeeperException, InterruptedException {
        logger.debug("[begin] 创建根节点:{}",path);
        zooKeeper.create(path, "测试根节点数据".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        logger.debug("[end]   创建根节点:{}",path);

        bindToNode(BindBehavior.getChildren, path);          //!!!

        logger.debug("[begin] 修改根节点数据");
        zooKeeper.setData(path, "修改根节点数据".getBytes(), -1);
        logger.debug("[end]   修改根节点数据");

        bindToNode(BindBehavior.getChildren, path);          //!!!

        logger.debug("[begin] 创建子节点:{}", path_son);
        zooKeeper.create(path_son, "修改子节点数据".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        logger.debug("[end]   创建子节点:{}", path_son);

        bindToNode(BindBehavior.getChildren, path);          //!!!

        logger.debug("[begin] 修改子节点数据");
        zooKeeper.setData(path_son, "修改子节点数据".getBytes(), -1);
        logger.debug("[end]   修改子节点数据");

        bindToNode(BindBehavior.getChildren, path);          //!!!

        logger.debug("[begin] 删除子节点:{}", path_son);
        zooKeeper.delete(path_son, -1);
        logger.debug("[end]   删除子节点:{}", path_son);

        bindToNode(BindBehavior.getChildren, path);          //!!!

        logger.debug("[begin] 删除根节点:{}",path);
        zooKeeper.delete(path, -1);
        logger.debug("[end]   删除根节点:{}",path);
    }
    /************************************************getChildren end***********************************************/

    /************************************************getData begin***********************************************/
    /**
     * 测试getData方法所绑定的watcher对于自身节点是否响应
     * @throws KeeperException
     * @throws InterruptedException
     */
    private static void getDataToRoot() throws KeeperException, InterruptedException {

        logger.debug("[begin] 创建根节点:{}",path);
        zooKeeper.create(path, "测试根节点数据".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        logger.debug("[end]   创建根节点:{}",path);

        bindToNode(BindBehavior.getData, path);      //!!!

        logger.debug("[begin] 修改根节点数据");
        zooKeeper.setData(path, "修改根节点数据".getBytes(), -1);
        logger.debug("[end]   修改根节点数据");

        bindToNode(BindBehavior.getData, path);      //!!!

        logger.debug("[begin] 删除根节点:{}",path);
        zooKeeper.delete(path, -1);
        logger.debug("[end]   删除根节点:{}",path);
    }

    /**
     * 测试getData方法所绑定的watcher对于子节点是否响应
     * @throws KeeperException
     * @throws InterruptedException
     */
    private static void getDataToSon() throws KeeperException, InterruptedException {
        logger.debug("[begin] 创建根节点:{}",path);
        zooKeeper.create(path, "测试根节点数据".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        logger.debug("[end]   创建根节点:{}",path);

        bindToNode(BindBehavior.getData, path);          //!!!

        logger.debug("[begin] 修改根节点数据");
        zooKeeper.setData(path, "修改根节点数据".getBytes(), -1);
        logger.debug("[end]   修改根节点数据");

        bindToNode(BindBehavior.getData, path);          //!!!

        logger.debug("[begin] 创建子节点:{}", path_son);
        zooKeeper.create(path_son, "修改子节点数据".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        logger.debug("[end]   创建子节点:{}", path_son);

        bindToNode(BindBehavior.getData, path);          //!!!

        logger.debug("[begin] 修改子节点数据");
        zooKeeper.setData(path_son, "修改子节点数据".getBytes(), -1);
        logger.debug("[end]   修改子节点数据");

        bindToNode(BindBehavior.getData, path);          //!!!

        logger.debug("[begin] 删除子节点:{}", path_son);
        zooKeeper.delete(path_son, -1);
        logger.debug("[end]   删除子节点:{}", path_son);

        bindToNode(BindBehavior.getData, path);          //!!!

        logger.debug("[begin] 删除根节点:{}",path);
        zooKeeper.delete(path, -1);
        logger.debug("[end]   删除根节点:{}",path);
    }
    /************************************************getData end***********************************************/

    private static int count = 0;

    private static void bindToNode(final BindBehavior bindBehavior, String nodePath) throws KeeperException, InterruptedException {

        logger.debug("绑定行为{}的Watcher到节点{}", bindBehavior.name(), nodePath);

        Watcher watcher = new Watcher() {
            public void process(WatchedEvent event) {
                logger.debug("------>{}行为{}被触发Watcher,类型:{},节点:{}",count++, bindBehavior.name(), event.getType(), event.getPath());
            }
        };

        switch (bindBehavior){
            case exists:
                zooKeeper.exists(nodePath, watcher);
                break;
            case getChildren:
                zooKeeper.getChildren(nodePath, watcher);
                break;
            case getData:
                zooKeeper.getData(nodePath, watcher, null);
        }

    }

    enum BindBehavior{
        exists,
        getChildren,
        getData;
    }
}
