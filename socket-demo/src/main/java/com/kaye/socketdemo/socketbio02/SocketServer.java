package com.kaye.socketdemo.socketbio02;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 升级版 socket server
 *
 * @author yk
 * @since 2019/2/13$ 15:56$
 */
public class SocketServer {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            //创建一个socket服务
            serverSocket = new ServerSocket(11111);
            System.err.println("socket服务已经启动等待接受数据");
            //进行阻塞 一次accept 处理一个client
            Socket socket = null;
            //手动创建一个线程池 防止client端太多服务蹦掉
            HandlerExecutorPool handlerExecutorPool = new HandlerExecutorPool(50, 1000);
            while (true) {
                socket = serverSocket.accept();
                handlerExecutorPool.execute(new ServerHandler(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
