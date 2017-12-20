package com.nvisary;

import java.io.*;
import java.net.*;

public class SendThread extends Thread {

    private final InetAddress address;
    private final int port;
    private String name;
    private DatagramSocket socket;

    public SendThread(DatagramSocket socket, InetAddress address, int port, String name){
        this.name = name;
        this.address = address;
        this.port = port;
        this.socket = socket;
    }

    @Override
    public void run() {
        while (true) {
            if (address != null && port != 0) {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    String readLine = reader.readLine();

                    if (readLine.equals("@quit")) {
                        System.exit(0);
                    }

                    if (readLine.length() > 5 && readLine.substring(0, 5).equals("@name")) {
                        name = readLine.substring(6, readLine.length());
                        System.out.println("Имя изменено.");
                        continue;
                    }
                    readLine = name + " : " + readLine;

                    DatagramPacket packet = new DatagramPacket(readLine.getBytes(), readLine.getBytes().length, address, port);
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getUserName() {
        return name;
    }
}
