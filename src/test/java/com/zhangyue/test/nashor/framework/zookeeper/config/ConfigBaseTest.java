package com.zhangyue.test.nashor.framework.zookeeper.config;

import org.junit.After;
import org.junit.Before;

import com.zhangyue.test.nashor.framework.zookeeper.common.curator.ZkCuratorService;
import com.zhangyue.test.nashor.framework.zookeeper.example.config.ConfigService;
import com.zhangyue.test.nashor.framework.zookeeper.example.config.ZkConfigService;

/**
 * @author YanMeng
 * @date 16-11-8
 */
public class ConfigBaseTest {
    ZkCuratorService zkService = null;
    ConfigService configService = null;

    @Before
    public void before(){
        String zkConnInfo = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183,127.0.0.1:2184,127.0.0.1:2185";
        zkService = new ZkCuratorService(zkConnInfo);
        configService = new ZkConfigService(zkService);
    }

    @After
    public void after(){
        zkService.destory();
        zkService = null;
        configService = null;
    }
}
