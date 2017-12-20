package com.nvisary.server;


import com.nvisary.ReceiveThread;
import com.nvisary.SendThread;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/*
    Добавить команду @cat filename для просмотра текстовых файлов из файловой системы собеседника (на TCP).
*/

public class Server {
    private DatagramSocket serverSocket;

    public Server(int port) throws SocketException {
        serverSocket = new DatagramSocket(port);
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 1) {
            int port = Integer.parseInt(args[0]);
            Server server = new Server(port);
            server.run();

        } else {
            System.out.println("Не те аргументы");
        }

    }

    public void run() {
        ReceiveThread receiveThread = new ReceiveThread(serverSocket);
        receiveThread.start();

        InetAddress clientAddress = receiveThread.getAddress();
        int clientPort = receiveThread.getPort();

        while (clientPort == 0 || clientAddress == null) {
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clientAddress = receiveThread.getAddress();
            clientPort = receiveThread.getPort();
        }
        SendThread sendThread = new SendThread(serverSocket, clientAddress, clientPort, "Server");
        sendThread.start();

    }
}
