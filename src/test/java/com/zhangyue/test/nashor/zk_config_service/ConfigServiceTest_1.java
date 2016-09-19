package com.zhangyue.test.nashor.zk_config_service;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

import com.zhangyue.test.nashor.framework.zookeeper.example.ConfigService.ConfigService;

/**
 * zk原生api实现的配置中心 测试
 *
 * 模拟另一应用新增/更新/删除配置
 *
 * @author YanMeng
 * @date 16-9-14
 */
public class ConfigServiceTest_1 {

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        ConfigService configService = new ConfigService();
        configService.init("127.0.0.1", 2182);
        configService.saveConfig("a.b.c.g", "test");
        configService.updateConfig("a.b.c.g", "testtest");
        configService.updateConfig("a.b.c.g", "testtesttesttest");
        configService.removeConfig("a.b.c.g");
    }
}
