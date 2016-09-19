package com.zhangyue.test.nashor.zk_config_service;

import java.util.Date;
import java.util.Map;

import com.zhangyue.test.nashor.framework.zookeeper.curator.ConfigService;
import com.zhangyue.test.nashor.framework.zookeeper.curator.common.ZkService;
import com.zhangyue.test.nashor.framework.zookeeper.curator.config_service.ZkConfigService;

/**
 * curator实现的配置中心 测试
 *
 * 模拟一直持有配置中心实例, 每过一段时间查询配置中心的数据内容
 *
 * @author YanMeng
 * @date 16-9-19
 */
public class CuratorTest_0 {

    public static void main(String[] args) throws InterruptedException {

        String zkConnInfo = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183,127.0.0.1:2184,127.0.0.1:2185";

        ZkService zkService = new ZkService(zkConnInfo);
        ConfigService configService = new ZkConfigService(zkService);


        while (true){
            Map<String, String> configs = configService.getConfigs();
            for(String key : configs.keySet()){
                System.out.println(key + " - " + configs.get(key));
            }
            System.out.println("====================" + new Date());
            Thread.currentThread().sleep(15000);

        }
    }
}
