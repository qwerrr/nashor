package com.zhangyue.test.nashor;

import java.io.IOException;
import java.net.Socket;

/**
 * @author YanMeng
 * @date 16-7-14
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",5554);
//        OutputStream out = socket.getOutputStream();
//        InputStream in = socket.getInputStream();
        System.out.printf("123");

    }
}
