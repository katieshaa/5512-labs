package com.nvisary;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;


public class ReceiveThread extends Thread{
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
                /*Доп********************************************/
                if (str.startsWith("@cat")) {
                    String fileName = str.substring(5);

                    System.out.println("Sending " + fileName);
                    StringBuilder sb = new StringBuilder();

                    for (int i = 0; i < fileName.length(); i++) {
                        if (fileName.charAt(i) != 0) {
                            sb.append(fileName.charAt(i));
                        }
                    }
                    fileName = sb.toString();
                    File file = new File(fileName); 
                    Socket socket = new Socket(address, 4564);   

                    
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                    
                    byte[] arr = Files.readAllBytes(file.toPath());
                    
                    objectOutputStream.writeObject(arr);
                    
                    objectOutputStream.flush();
                    
                    objectOutputStream.close();
                    
                    socket.close();

                    System.out.println("File send success.");
                    continue;
                }
                /*Доп end******************************************/
                System.out.println(str);
            } catch (IOException e) {
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
