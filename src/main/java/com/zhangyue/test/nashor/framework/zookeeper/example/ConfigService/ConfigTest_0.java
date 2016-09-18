package com.zhangyue.test.nashor.framework.zookeeper.example.ConfigService;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

/**
 * @author YanMeng
 * @date 16-9-14
 */
public class ConfigTest_0 {

    public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
        ConfigService configService = new ConfigService();
        configService.init("127.0.0.1", 2182);
        configService.saveConfig("a.b.c.g", "test");
        configService.updateConfig("a.b.c.g", "testtest");
        configService.updateConfig("a.b.c.g", "testtesttesttest");
        configService.removeConfig("a.b.c.g");
    }

}
