package com.zhangyue.test.nashor.java.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * @author YanMeng
 * @date 17-3-20.
 */
public class NioServer {

    public void test(){
        ServerSocketChannel serverSocketChannel = null;
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", 9088));
            serverSocketChannel.configureBlocking(false);

            Selector selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
