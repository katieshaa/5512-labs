package com.kudrek.client;

import com.kudrek.ReceiveThread;
import com.kudrek.SendThread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

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
            System.out.println("Arg Error");
        }
    }

}
