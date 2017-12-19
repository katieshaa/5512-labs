package com.nvisary.client;

import com.nvisary.util.BeautyConsole;
import com.nvisary.util.Message;

import java.io.*;
import java.net.Socket;


public class Client {
    private Socket socket;
    private int serverPort;
    private String serverIp;

    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private String userName = "null";
    private boolean changeName = true;

    public Client(String serverIp, int serverPort) {
        this.serverPort = serverPort;
        this.serverIp = serverIp;
    }

    public void start() throws IOException {
        socket = new Socket(serverIp, serverPort);
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        Thread receive = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    while (true) {
                        Message message = (Message) objectInputStream.readObject();
                        if (message.getMessageType().equals("error")) {
                            userName = "null";
                            BeautyConsole.redPrintLn(message.getMsg());
                            changeName = true;
                            continue;
                        }

                        if (!message.getDestinationUser().equals("null")) {
                            BeautyConsole.print(message.getUserName(), message.getNameColor());
                            BeautyConsole.greenPrint(" : " + message.getMsg() + "\n");
                        } else {
                            
                            BeautyConsole.print(message.getUserName(), message.getNameColor());
                            System.out.print(" : " + message.getMsg() + "\n");
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        receive.start();

        Thread send = new Thread(new Runnable() {
            @Override
            public void run() {
                String destinationName;
                while (true) {
                    try {
                        if (changeName) {
                            System.out.println("Please input your name: ");
                            userName = reader.readLine();
                            changeName = false;
                            continue;
                        }
                        destinationName = "null";
                        try {
                            receive.join(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        StringBuilder msg = new StringBuilder(reader.readLine());

                        if (msg.toString().contains("@user")) {
                            String[] parts = msg.toString().split("\\s+");
                            destinationName = parts[1];
                            msg = new StringBuilder();
                            for (int i = 2; i < parts.length; i++) {
                                msg.append(parts[i]).append(" ");
                            }
                        }
                        Message message = new Message(msg.toString(), userName, destinationName);
                        if (msg.toString().startsWith("@alarm")) {
                            message.setMessageType("alarm");

                        }

                        objectOutputStream.writeObject(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        send.start();
    }
}
