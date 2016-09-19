package com.zhangyue.test.nashor.zk_config_service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.apache.zookeeper.KeeperException;

import com.zhangyue.test.nashor.framework.zookeeper.example.ConfigService.ConfigService;

/**
 * zk原生api实现的配置中心 测试
 *
 * 模拟一直持有配置中心实例, 每过一段时间查询配置中心的数据内容
 *
 * @author YanMeng
 * @date 16-9-14
 */
public class ConfigServiceTest_0 {

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
