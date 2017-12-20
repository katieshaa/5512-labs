package com.kudrek;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.io.*;

public class ReceiveThread extends Thread {
    private DatagramSocket socket;
    private InetAddress address = null;
    private int port = 0;

    public ReceiveThread(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(new byte[100], 100);
                socket.receive(packet);
                address = packet.getAddress();
                port = packet.getPort();
                String str = new String(packet.getData());
                System.out.println(str);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

}
