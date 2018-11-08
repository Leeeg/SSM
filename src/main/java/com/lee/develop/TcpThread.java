package com.lee.develop;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 18-10-25
 * @Time 上午9:37
 */
public class TcpThread extends Thread {

    private Socket socket;
    private SocketServer server;
    private DataInputStream in = null;
    private byte buffer[] = new byte[140];
    private String temp;

    public TcpThread(Socket socket, SocketServer server) {
        this.socket = socket;
        this.server = server;
        init();
    }

    private void init() {
        try {
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        System.out.println("子线程开始工作");
        while (true) {
            try {
                System.out.println("线程" + this.getId() + ":开始从客户端读取数据——>");
                while (in.read(buffer) != -1) {
                    server.receiveMsg(buffer);
                    server.sendMsgToAll(socket, buffer);
                }
                if (socket.getKeepAlive() == false) {
                    in.close();
                    temp = "客户端" + socket.getPort() + ":退出";
                    System.out.println(temp);
                    socket.close();
                    this.stop();
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    in.close();
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

    }
}
