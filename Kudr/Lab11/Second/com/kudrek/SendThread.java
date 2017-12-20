package com.kudrek;

import java.io.*;
import java.net.*;
import java.nio.file.Files;


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
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            if (address != null && port != 0) {
                try {

                    String readLine = reader.readLine();

                    if (readLine.equals("@quit")) {
                        System.exit(0);
                    }

                    if (readLine.length() > 5 && readLine.substring(0, 5).equals("@name")) {
                        name = readLine.substring(6, readLine.length());
                        System.out.println("Name changed");
                        continue;
                    }

                    if (readLine.startsWith("@send")) {
                        String fileName = readLine.substring(6);

                        DatagramPacket packet = new DatagramPacket(readLine.getBytes(), readLine.getBytes().length, address, port);
                        socket.send(packet);

                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < fileName.length(); i++) {
                            if (fileName.charAt(i) != 0) {
                                sb.append(fileName.charAt(i));
                            }
                        }
                        fileName = sb.toString();
                        File file = new File(fileName); 
                        Socket socket = new Socket(address, 2525);   
                       
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());


                        byte[] arr = Files.readAllBytes(file.toPath());

                        objectOutputStream.writeObject(arr);

                        objectOutputStream.flush();

                        objectOutputStream.close();

                        socket.close();

                        System.out.println("File send success.");
                        continue;
                    }
                    readLine = name + " : " + readLine;

                    DatagramPacket packet = new DatagramPacket(readLine.getBytes(), readLine.getBytes().length, address, port);
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getUserName() {
        return name;
    }
}
