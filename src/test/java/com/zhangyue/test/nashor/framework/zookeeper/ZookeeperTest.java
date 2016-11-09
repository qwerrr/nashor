package com.zhangyue.test.nashor.framework.zookeeper;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.*;

import com.zhangyue.test.nashor.framework.zookeeper.common.ZkConnFactory;

/**
 * zookeeper 基本测试
 * @author YanMeng
 * @date 16-8-8
 */
public class ZookeeperTest {



    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {

        ZooKeeper zooKeeper = ZkConnFactory.getZk();

        //创建根znode
        zooKeeper.create("/testZk", "测试根目录".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        //创建子znode
        zooKeeper.create("/testZk/testChildren", "测试子目录123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

        //查询数据
        byte[] data = zooKeeper.getData("/testZk", false, null);
        System.out.println("/testZk查询数据结果:"+new String(data));

        //查询子节点
        List<String> children = zooKeeper.getChildren("/testZk", true);
        System.out.println("/testZk查询子节点结果:"+children);

        //修改子节点数据
        zooKeeper.setData("/testZk/testChildren", "测试子目录456".getBytes(), -1);     //-1:匹配所有版本
        data = zooKeeper.getData("/testZk/testChildren", false, null);
        System.out.println("/testZk/testChildren查询修改后数据结果:"+new String(data));

        //创建子znode
        zooKeeper.create("/testZk/testChildren1", "测试子目录789".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        children = zooKeeper.getChildren("/testZk", false);
        System.out.println("/testZk查询子节点结果:"+children);


        //删除子znode
        zooKeeper.delete("/testZk/testChildren1", -1);  //-1:匹配所有版本
        zooKeeper.delete("/testZk/testChildren", -1);   //-1:匹配所有版本

        //删除根znode
        zooKeeper.delete("/testZk", -1);                //-1:匹配所有版本

        zooKeeper.close();
    }
}
