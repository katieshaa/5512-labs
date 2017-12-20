package com.nvisary.client;

import com.nvisary.client.Client;

import java.io.IOException;


public class RunClient {
    public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            String serverIp = args[0];
            int serverPort = Integer.parseInt(args[1]);
            Client client = new Client(serverIp, serverPort);
            client.start();
        } else {
            System.out.println("Please input server ip and server port...");
        }
    }
}
