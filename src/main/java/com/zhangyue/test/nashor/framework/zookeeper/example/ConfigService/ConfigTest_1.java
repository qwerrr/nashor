package com.zhangyue.test.nashor.framework.zookeeper.example.ConfigService;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

/**
 * @author YanMeng
 * @date 16-9-14
 */
public class ConfigTest_1 {
    public static void main(String[] args) throws IOException {
        try {
            ConfigService.init("127.0.0.1", 2181);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
