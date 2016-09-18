package com.zhangyue.test.nashor.framework.zookeeper.example.ConfigService;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.apache.zookeeper.KeeperException;

/**
 * @author YanMeng
 * @date 16-9-14
 */
public class ConfigTest_1 {
    public static void main(String[] args) throws IOException {

        try {
            ConfigService configService = new ConfigService();
            configService.init("127.0.0.1", 2181);

            while (true){
                Thread.currentThread().sleep(15000);
                Map<String, String> configs = configService.getConfigs();
                for(String key : configs.keySet()){
                    System.out.println(key + " - " + configs.get(key));
                }
                System.out.println("====================" + new Date());
            }

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
