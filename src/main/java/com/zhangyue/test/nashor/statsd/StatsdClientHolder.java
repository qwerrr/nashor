package com.zhangyue.test.nashor.statsd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 *
 */
public class StatsdClientHolder {

    private static Logger logger = LoggerFactory.getLogger(StatsdClientHolder.class);
    private static StatsdClient sc = null;
    private static boolean inited = false;

    private static final boolean NON_BLOCKING = true;
    private static final short BUFFER_SIZE = 1000;

    /**
     * @param host 域名
     * @param port 端口, 使用时尽量优先默认端口: 8125
     * @return
     */
    public static boolean init(String host, int port){
        return init(host, port, NON_BLOCKING, BUFFER_SIZE);
    }

    /**
     * @param host 域名
     * @param port 端口, 使用时尽量优先默认端口: 8125
     * @param unBlocking true为非阻塞模式, 默认true
     * @param bufferSize udp队列大小, 默认1000, 根据使用场景增加
     * @return
     */
    public synchronized static boolean init(String host, int port, boolean unBlocking, short bufferSize){

        if(inited){
            throw new IllegalStateException("StatsdClient已被初始化!");
        }

        try {
            sc = new StatsdClient(host, port, unBlocking, bufferSize);
        } catch (IOException e) {
            logger.error("StatsdClient初始化失败:", e);
        }

        if(sc != null) inited = true;

        return true;
    }

    public static StatsdClient getClient(){
        if(!inited){
            throw new IllegalStateException("StatsdClient未初始化!");
        }
        return sc;
    }
}
