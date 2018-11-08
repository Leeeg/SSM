package com.lee.develop;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 18-10-25
 * @Time 上午9:37
 */
public class SocketServer {

    private static Map<Integer, Socket> clients = new HashMap<Integer, Socket>();

    public static void main(String[] args) {

        new SocketServer().init();

    }

    private void init() {

        new UdpThread(8889).start();

        try {
            //1.创建一个服务器端Socket，即SocketThread，指定绑定的端口，并监听此端口
            ServerSocket serverSocket = new ServerSocket(8888);
            Socket socket = null;
            InetAddress address = null;
            //记录客户端的数量
            int count = 0;
            System.out.println("***TCP 服务器启动，等待客户端的连接***");
            //循环监听等待客户端的连接
            while (true) {
                //调用accept()方法开始监听，等待客户端的连接
                socket = serverSocket.accept();
                //创建一个新的线程
                new TcpThread(socket, this).start();

                address = socket.getInetAddress();
                System.out.println("当前客户端的IP：" + address.getHostAddress() + "    " + socket.getPort());

                clients.put(socket.getPort(), socket);
                count++;//统计客户端的数量
                System.out.println("客户端的数量：" + count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveMsg(byte[] data) {
        System.out.println("message receive : lenth = " + data.length);

    }

    public void sendMsgToAll(Socket fromSocket, byte[] data) {
        System.out.println("message sendMsgToAll : lenth = " + data.length);
        Set<Integer> keyset = this.clients.keySet();
        java.util.Iterator<Integer> iter = keyset.iterator();
        while (iter.hasNext()) {
            int key = iter.next();
            Socket socket = clients.get(key);
            if (socket != fromSocket) {
                try {
                    if (socket.isClosed() == false) {
                        if (socket.isOutputShutdown() == false) {
                            System.out.println("message send : lenth = " + data.length + "     to " + socket.getPort());
                            OutputStream out = new DataOutputStream(socket.getOutputStream());
                            out.write(data);
                            out.flush();
                        }
                    }
                } catch (SocketException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
    }


}
