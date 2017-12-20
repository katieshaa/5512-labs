package com.nvisary.client;

import com.nvisary.ReceiveThread;
import com.nvisary.SendThread;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {
    private int serverPort;
    private DatagramSocket clientSocket;
    private InetAddress serverAddress;

    public Client(String ip, int port) throws SocketException, UnknownHostException {
        String serverIp = ip;
        serverPort = port;

        clientSocket = new DatagramSocket();
        serverAddress = InetAddress.getByName(serverIp);
    }


    public void run() {
        SendThread sendThread = new SendThread(clientSocket, serverAddress, serverPort, "Client");
        sendThread.start();

        ReceiveThread receiveThread = new ReceiveThread(clientSocket);
        receiveThread.start();
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            String ip = args[0];
            int port = Integer.parseInt(args[1]);
            Client client = new Client(ip, port);
            client.run();
        } else {
            System.out.println("Не те аргументы");
        }
    }

}
