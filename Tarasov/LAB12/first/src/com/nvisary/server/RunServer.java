package com.nvisary.server;

import java.io.IOException;


public class RunServer {
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
        Server server;
        int serverPort = 7777;
        if (args.length == 1) {
            serverPort = Integer.parseInt(args[0]);
        }
        server = new Server(serverPort);
        server.start();
    }
}
