package com.zhangyue.test.nashor.zk_config_service;

import com.zhangyue.test.nashor.framework.zookeeper.curator.ConfigService;
import com.zhangyue.test.nashor.framework.zookeeper.curator.common.ZkService;
import com.zhangyue.test.nashor.framework.zookeeper.curator.config_service.ZkConfigService;

/**
 * curator实现的配置中心 测试
 *
 * 模拟另一应用新增/更新/删除配置
 *
 * @author YanMeng
 * @date 16-9-19
 */
public class CuratorTest_1 {

    public static void main(String[] args) throws InterruptedException {

        String zkConnInfo = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183,127.0.0.1:2184,127.0.0.1:2185";

        ZkService zkService = new ZkService(zkConnInfo);
        ConfigService configService = new ZkConfigService(zkService);
        configService.saveConfig("a.b.c.g", "test");
        configService.updateConfig("a.b.c.g", "testtest");
        configService.updateConfig("a.b.c.g", "testtesttesttest");
        configService.removeConfig("a.b.c.g");
        Thread.currentThread().sleep(2000);
        zkService.destory();
    }
}
