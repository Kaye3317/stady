package com.kaye.socketdemo.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * client
 *
 * @author yk
 * @since 2019/2/13$ 15:56$
 */
public class SocketClient {

    public static void main(String[] args) throws IOException {
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            //连接到ServerSocket
            socket = new Socket("127.0.0.1", 11111);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("客户端发送了一条数据.....");
            //接受服务器响应的数据
            String respones = in.readLine();
            System.err.println("client: " + respones);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
        }
    }
}
