package com.zhangyue.test.nashor.zookeeper.cluster;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import com.zhangyue.test.nashor.zookeeper.ZkConnFactory;

/**
 * 一致性测试
 *
 * @author YanMeng
 * @date 16-9-2
 */
public class ConsistencyTest {

    static String PATH = "/ConsistencyTest";

    public static void main(String[] args) throws IOException, InterruptedException {
        String ip = "127.0.0.1";
        int serverPort1 = 2181;
        int serverPort2 = 2182;
        int serverPort3 = 2183;
        int serverPort4 = 2184;
        int serverPort5 = 2185;

        ZooKeeper zooKeeper1 = ZkConnFactory.getZk(ip, serverPort1);
        ZooKeeper zooKeeper2 = ZkConnFactory.getZk(ip, serverPort2);
        ZooKeeper zooKeeper3 = ZkConnFactory.getZk(ip, serverPort3);
        ZooKeeper zooKeeper4 = ZkConnFactory.getZk(ip, serverPort4);
        ZooKeeper zooKeeper5 = ZkConnFactory.getZk(ip, serverPort5);

        Object lock = new Object();
        ReadThread readThread1 = new ReadThread(zooKeeper1, lock, "1");
        ReadThread readThread2 = new ReadThread(zooKeeper2, lock, "2");
        ReadThread readThread3 = new ReadThread(zooKeeper3, lock, "3");
        ReadThread readThread4 = new ReadThread(zooKeeper4, lock, "4");
        WriteThread writeThread = new WriteThread(zooKeeper5, lock);

        new Thread(readThread1).start();
        new Thread(readThread2).start();
        new Thread(readThread3).start();
        new Thread(readThread4).start();

        Thread.currentThread().sleep(1000);

        new Thread(writeThread).start();
    }


}

/**
 * 写入线程
 */
class WriteThread implements Runnable{

    private ZooKeeper zooKeeper;
    private Object lock;        //lock用于保证写入操作在读取操作之前进行

    WriteThread(ZooKeeper zooKeeper, Object lock){
        this.zooKeeper = zooKeeper;
        this.lock = lock;
    }

    public void run() {
        synchronized (lock){
            try {
                if(null != zooKeeper.exists(ConsistencyTest.PATH, false)){
                    zooKeeper.delete(ConsistencyTest.PATH, -1);
                }
                zooKeeper.create(ConsistencyTest.PATH, "ConsistencyTest".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                System.out.println("写入完成,释放锁");
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.notifyAll();
            }
        }
    }
}

/**
 * 读取线程
 */
class ReadThread implements Runnable{

    private ZooKeeper zooKeeper;
    private Object lock;        //lock用于保证写入操作在读取操作之前进行, 并且写入后立即读取
    private String name;

    ReadThread(ZooKeeper zooKeeper, Object lock, String name){
        this.zooKeeper = zooKeeper;
        this.lock = lock;
        this.name = name;
    }

    public void run() {
        try {
            synchronized (lock){
                System.out.println("[".concat(name).concat("]等待写入"));
                lock.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("[".concat(name).concat("]已得知写入完成"));
        try {
            Stat stat = zooKeeper.exists(ConsistencyTest.PATH, false);
            System.out.println("测试写入是否已经同步到实例[".concat(name).concat("], 结果:") + (stat != null));
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
