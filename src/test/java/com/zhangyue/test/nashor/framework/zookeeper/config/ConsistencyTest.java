package com.zhangyue.test.nashor.framework.zookeeper.config;

import org.junit.Test;

/**
 * 配置中心测试
 * 一致性测试
 *218
 * @author YanMeng
 * @date 16-11-8
 */
public class ConsistencyTest extends ConfigBaseTest{

    private static int THOUSAND = 1000;
    private static String CONSISTENCY_NUMBER = "ConsistencyNumber";

    /**
     *
     Client1:

     2016-11-08 19:39:10,651 DEBUG ConfUpdateWatcher - [配置中心回调]修改 key - ConsistencyNumber; value - 728
     2016-11-08 19:39:10,652 DEBUG ClientCnxn - Reading reply sessionid:0x15843a05e570000, packet:: clientPath:null serverPath:null finished:false header:: 3981,3  replyHeader:: 3981,51539610271,0  request:: '/ConfigService/ConsistencyNumber,F  response:: s{51539607555,51539610271,1478604686867,1478605150555,2709,0,0,0,3,0,51539607555}
     2016-11-08 19:39:10,652 DEBUG ClientCnxn - Reading reply sessionid:0x15843a05e570000, packet:: clientPath:null serverPath:null finished:false header:: 3982,4  replyHeader:: 3982,51539610271,0  request:: '/ConfigService/ConsistencyNumber,T  response:: #373238,s{51539607555,51539610271,1478604686867,1478605150555,2709,0,0,0,3,0,51539607555}
     2016-11-08 19:39:10,710 DEBUG ClientCnxn - Got notification sessionid:0x15843a05e570000
     2016-11-08 19:39:10,710 DEBUG ClientCnxn - Got WatchedEvent state:SyncConnected type:NodeDataChanged path:/ConfigService/ConsistencyNumber for sessionid 0x15843a05e570000
     2016-11-08 19:39:10,711 DEBUG NodeDataChangedWatcher - ==NodeDataChangedWatcher - dataChangedCallBack==
     2016-11-08 19:39:10,759 DEBUG ClientCnxn - Reading reply sessionid:0x15843a05e570000, packet:: clientPath:null serverPath:null finished:false header:: 3983,5  replyHeader:: 3983,51539610273,0  request:: '/ConfigService/ConsistencyNumber,#373238,-1  response:: s{51539607555,51539610273,1478604686867,1478605150653,2711,0,0,0,3,0,51539607555}
     2016-11-08 19:39:10,759 DEBUG ClientCnxn - Reading reply sessionid:0x15843a05e570000, packet:: clientPath:null serverPath:null finished:false header:: 3984,4  replyHeader:: 3984,51539610273,0  request:: '/ConfigService/ConsistencyNumber,F  response:: #373238,s{51539607555,51539610273,1478604686867,1478605150653,2711,0,0,0,3,0,51539607555}
     =================================
     2016-11-08 19:39:10,759 DEBUG ConfUpdateWatcher - [配置中心回调]修改 key - ConsistencyNumber; value - 728
     运行时间: 106888 ms
     2016-11-08 19:39:10,760 DEBUG CuratorFrameworkImpl - Closing
     2016-11-08 19:39:10,760 DEBUG ClientCnxn - Reading reply sessionid:0x15843a05e570000, packet:: clientPath:null serverPath:null finished:false header:: 3985,4  replyHeader:: 3985,51539610273,0  request:: '/ConfigService/ConsistencyNumber,T  response:: #373238,s{51539607555,51539610273,1478604686867,1478605150653,2711,0,0,0,3,0,51539607555}
     2016-11-08 19:39:10,761 DEBUG CuratorZookeeperClient - Closing
     2016-11-08 19:39:10,761 DEBUG ConnectionState - Closing
     2016-11-08 19:39:10,763 DEBUG ZooKeeper - Closing session: 0x15843a05e570000
     2016-11-08 19:39:10,763 DEBUG ClientCnxn - Closing client for session: 0x15843a05e570000
     java.lang.IllegalStateException: instance must be started before calling this method
     2016-11-08 19:39:10,810 DEBUG ClientCnxn - Got notification sessionid:0x15843a05e570000
     at com.google.common.base.Preconditions.checkState(Preconditions.java:176)
     at org.apache.curator.framework.imps.CuratorFrameworkImpl.getData(CuratorFrameworkImpl.java:363)
     at com.zhangyue.test.nashor.framework.zookeeper.common.curator.ZkCuratorService.getData(ZkCuratorService.java:74)
     at com.zhangyue.test.nashor.framework.zookeeper.example.config.ConfUpdateWatcher.process(ConfUpdateWatcher.java:36)
     at com.zhangyue.test.nashor.framework.zookeeper.common.curator.watcher.behavior.NodeDataChangedWatcher.dataChangedCallBack(NodeDataChangedWatcher.java:23)
     at com.zhangyue.test.nashor.framework.zookeeper.common.curator.watcher.GetDataWatcher.process(GetDataWatcher.java:35)
     at org.apache.curator.framework.imps.NamespaceWatcher.process(NamespaceWatcher.java:61)
     at org.apache.zookeeper.ClientCnxn$EventThread.processEvent(ClientCnxn.java:522)
     at org.apache.zookeeper.ClientCnxn$EventThread.run(ClientCnxn.java:498)
     2016-11-08 19:39:10,811 DEBUG ClientCnxn - Got WatchedEvent state:SyncConnected type:NodeDataChanged path:/ConfigService/ConsistencyNumber for sessionid 0x15843a05e570000
     2016-11-08 19:39:10,811 DEBUG NodeDataChangedWatcher - ==NodeDataChangedWatcher - dataChangedCallBack==
     2016-11-08 19:39:10,811 ERROR ConfUpdateWatcher - 更新配置数据异常:
     2016-11-08 19:39:10,858 DEBUG ClientCnxn - Reading reply sessionid:0x15843a05e570000, packet:: clientPath:null serverPath:null finished:false header:: 3986,-11  replyHeader:: 3986,51539610275,0  request:: null response:: null
     2016-11-08 19:39:10,858 DEBUG ClientCnxn - Disconnecting client for session: 0x15843a05e570000
     2016-11-08 19:39:10,859 INFO  ZooKeeper - Session: 0x15843a05e570000 closed
     2016-11-08 19:39:10,859 INFO  ClientCnxn - EventThread shut down

     Client2:


     2016-11-08 19:39:12,843 DEBUG ConfUpdateWatcher - [配置中心回调]修改 key - ConsistencyNumber; value - 753
     2016-11-08 19:39:12,844 DEBUG ClientCnxn - Reading reply sessionid:0x25843a05e3f0002, packet:: clientPath:null serverPath:null finished:false header:: 3975,3  replyHeader:: 3975,51539610311,0  request:: '/ConfigService/ConsistencyNumber,F  response:: s{51539607555,51539610311,1478604686867,1478605152805,2748,0,0,0,3,0,51539607555}
     2016-11-08 19:39:12,844 DEBUG ClientCnxn - Reading reply sessionid:0x25843a05e3f0002, packet:: clientPath:null serverPath:null finished:false header:: 3976,4  replyHeader:: 3976,51539610311,0  request:: '/ConfigService/ConsistencyNumber,T  response:: #373533,s{51539607555,51539610311,1478604686867,1478605152805,2748,0,0,0,3,0,51539607555}
     2016-11-08 19:39:12,910 DEBUG ClientCnxn - Got notification sessionid:0x25843a05e3f0002
     2016-11-08 19:39:12,910 DEBUG ClientCnxn - Got WatchedEvent state:SyncConnected type:NodeDataChanged path:/ConfigService/ConsistencyNumber for sessionid 0x25843a05e3f0002
     2016-11-08 19:39:12,910 DEBUG NodeDataChangedWatcher - ==NodeDataChangedWatcher - dataChangedCallBack==
     2016-11-08 19:39:12,910 DEBUG ClientCnxn - Reading reply sessionid:0x25843a05e3f0002, packet:: clientPath:null serverPath:null finished:false header:: 3977,5  replyHeader:: 3977,51539610312,0  request:: '/ConfigService/ConsistencyNumber,#373533,-1  response:: s{51539607555,51539610312,1478604686867,1478605152844,2749,0,0,0,3,0,51539607555}
     =================================
     运行时间: 106520 ms
     2016-11-08 19:39:12,911 DEBUG ClientCnxn - Reading reply sessionid:0x25843a05e3f0002, packet:: clientPath:null serverPath:null finished:false header:: 3978,4  replyHeader:: 3978,51539610312,0  request:: '/ConfigService/ConsistencyNumber,F  response:: #373533,s{51539607555,51539610312,1478604686867,1478605152844,2749,0,0,0,3,0,51539607555}
     2016-11-08 19:39:12,911 DEBUG CuratorFrameworkImpl - Closing
     2016-11-08 19:39:12,911 DEBUG ConfUpdateWatcher - [配置中心回调]修改 key - ConsistencyNumber; value - 753
     2016-11-08 19:39:12,911 ERROR ConfUpdateWatcher - 更新配置数据异常:
     2016-11-08 19:39:12,912 DEBUG CuratorZookeeperClient - Closing
     2016-11-08 19:39:12,912 DEBUG ConnectionState - Closing
     2016-11-08 19:39:12,913 DEBUG ZooKeeper - Closing session: 0x25843a05e3f0002
     2016-11-08 19:39:12,913 DEBUG ClientCnxn - Closing client for session: 0x25843a05e3f0002
     java.lang.IllegalStateException: instance must be started before calling this method
     at com.google.common.base.Preconditions.checkState(Preconditions.java:176)
     at org.apache.curator.framework.imps.CuratorFrameworkImpl.getData(CuratorFrameworkImpl.java:363)
     at com.zhangyue.test.nashor.framework.zookeeper.common.curator.ZkCuratorService.nodeDataChangedCallBack(ZkCuratorService.java:107)
     at com.zhangyue.test.nashor.framework.zookeeper.example.config.ConfUpdateWatcher.process(ConfUpdateWatcher.java:41)
     at com.zhangyue.test.nashor.framework.zookeeper.common.curator.watcher.behavior.NodeDataChangedWatcher.dataChangedCallBack(NodeDataChangedWatcher.java:23)
     at com.zhangyue.test.nashor.framework.zookeeper.common.curator.watcher.GetDataWatcher.process(GetDataWatcher.java:35)
     at org.apache.curator.framework.imps.NamespaceWatcher.process(NamespaceWatcher.java:61)
     at org.apache.zookeeper.ClientCnxn$EventThread.processEvent(ClientCnxn.java:522)
     at org.apache.zookeeper.ClientCnxn$EventThread.run(ClientCnxn.java:498)
     2016-11-08 19:39:12,969 DEBUG ClientCnxn - Reading reply sessionid:0x25843a05e3f0002, packet:: clientPath:null serverPath:null finished:false header:: 3979,-11  replyHeader:: 3979,51539610313,0  request:: null response:: null
     2016-11-08 19:39:12,970 DEBUG ClientCnxn - Disconnecting client for session: 0x25843a05e3f0002
     2016-11-08 19:39:12,970 INFO  ZooKeeper - Session: 0x25843a05e3f0002 closed
     2016-11-08 19:39:12,970 INFO  ClientCnxn - EventThread shut down
     */


    /**
     * 获取config和设置config 在 zk上并非原子性的, 所以会出问题
     */
    @Deprecated
    @Test
    public void test(){
        //
        //configService.saveOrUpdateConfig(CONSISTENCY_NUMBER, "0");
        long begin = System.currentTimeMillis();
        for(int i = 0; i < THOUSAND; i++){
            configService.saveOrUpdateConfig(CONSISTENCY_NUMBER, String.valueOf(Integer.valueOf(configService.getConfig(CONSISTENCY_NUMBER)) + 1));
        }
        long end = System.currentTimeMillis();
        System.out.println("=================================");
        System.out.println("运行时间: " + (end - begin) + " ms");
    }
}
