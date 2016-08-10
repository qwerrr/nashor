package com.zhangyue.test.nashor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author YanMeng
 * @date 16-7-14
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5554);
        System.out.println("等待客户端连接:" + System.nanoTime());
        Socket socket = serverSocket.accept();
        System.out.println("客户端已连接:" + System.nanoTime());
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();
        System.out.println("客户端已获取inputstream:" + System.nanoTime());

    }
}
