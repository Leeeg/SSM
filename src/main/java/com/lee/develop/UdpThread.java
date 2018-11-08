package com.lee.develop;

import java.io.IOException;
import java.net.*;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 18-10-25
 * @Time 上午9:37
 */
public class UdpThread extends Thread {

    private int port;
    private DatagramSocket outDatagramSocket = null;
    private DatagramSocket ds = null;
    private DatagramPacket dpRcv;
    private DatagramPacket outPacket;
    private InetAddress local = null;
    private InetSocketAddress inetSocketAddress = null;
    private byte[] buffer = new byte[256];
    private int count;
    private String host = "192.168.1.174";

    public UdpThread(int mPort) {
        this.port = mPort;
    }

    @Override
    public void run() {
        inetSocketAddress = new InetSocketAddress(port);
        try {
            ds = new DatagramSocket(inetSocketAddress);
            System.out.println("***UDP 服务器启动***");
        } catch (SocketException e) {
            System.out.println("UDP服务创建失败");
            e.printStackTrace();
        }

        try {
            outDatagramSocket = new DatagramSocket();
            local = InetAddress.getByName(host);
        } catch (SocketException e) {
            System.out.println("UDP发送端初始化失败");
            e.printStackTrace();
        } catch (UnknownHostException e) {
            System.out.println("UDP发送端初始化失败");
            e.printStackTrace();
        }

        dpRcv = new DatagramPacket(buffer, buffer.length);
        while (true) {
            try {
//                Thread.sleep(3000);
                ds.receive(dpRcv);

//                String string = new String(dpRcv.getData(), dpRcv.getOffset(), dpRcv.getLength());
                System.out.println("收到信息：length = " + dpRcv.getLength() + "        count = " + (count += dpRcv.getLength()));

                byte[] testData = "test test test".getBytes();
                outPacket = new DatagramPacket(testData, testData.length, dpRcv.getAddress(), dpRcv.getPort());
                System.out.println("ip = " + outPacket.getAddress() + "     port = " + outPacket.getPort());

                outDatagramSocket.send(outPacket);

            } catch (IOException e) {
                System.out.println("UDP消息出错");
                e.printStackTrace();
            }
        }
    }

}
