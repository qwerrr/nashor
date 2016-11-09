package com.zhangyue.test.nashor.framework.zookeeper.config;

import java.util.Date;
import java.util.Map;

import org.junit.Test;

/**
 * 配置中心测试
 * 定时打印配置中心内数据
 *
 * @author YanMeng
 * @date 16-9-19
 */
public class QueryAndPrintTest extends ConfigBaseTest{

    @Test
    public void test() throws InterruptedException {
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
