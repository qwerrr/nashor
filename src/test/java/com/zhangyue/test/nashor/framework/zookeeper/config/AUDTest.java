package com.zhangyue.test.nashor.framework.zookeeper.config;

import org.junit.Test;

/**
 * 配置中心测试
 * 新增/修改/删除 某配置
 *
 * @author YanMeng
 * @date 16-9-19
 */
public class AUDTest extends ConfigBaseTest{

    @Test
    public void test() throws InterruptedException {
        configService.saveConfig("a.b.c.g", "test");
        configService.updateConfig("a.b.c.g", "testtest");
        configService.updateConfig("a.b.c.g", "testtesttesttest");
        configService.removeConfig("a.b.c.g");
        Thread.currentThread().sleep(2000);
    }

    @Test
    public void add(){
        configService.saveConfig("a.b.c.g", "test");
    }

    @Test
    public void update(){
        configService.updateConfig("a.b.c.g", "testtest");
        configService.updateConfig("a.b.c.g", "testtesttesttest");
    }

    @Test
    public void delete(){
        configService.removeConfig("a.b.c.g");
    }

}
