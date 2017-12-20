package com.nvisary.server;

import com.nvisary.util.Message;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class Connection extends Thread {
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    public static int id = 0;
    private String ip;
    private String userName = "Client" + id;
    private int colorName = 36;
    private Message outputMessage;
    private boolean newMessage;
    private Server server;

    public Connection(Socket socket, Server server) throws IOException {
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        ip = socket.getInetAddress().toString();
        newMessage = false;
        id++;
        this.server = server;

        int[] arr = {31, 32, 33, 34, 35, 36, 39, 93, 96, 91};
        Random rnd = new Random();
        colorName = arr[rnd.nextInt(arr.length)];
    }

    @Override
    public void run() {
        System.out.println(id + ": Thread started");
        while (true) {
            try {
                Message message = (Message) objectInputStream.readObject();
                System.out.println(id + ": Message received");
                if (!message.getUserName().equals(userName)) {
                    userName = message.getUserName();
                    if (server.nameIsFree(userName) ) {
                        server.addNewUserName(userName);
                    } else {
                        Message m = new Message("This name already exist.", "null");
                        m.setMessageType("error");
                        objectOutputStream.writeObject(m);
                        continue;
                    }
                }

                outputMessage = message;
                outputMessage.setNameColor(colorName);
                newMessage = true;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public Message getOutputMessage() {
        newMessage = false;
        return outputMessage;
    }

    public String getIp() {
        return ip;
    }

    public void sendMessageToUser(Message message) throws IOException {
        if (objectOutputStream != null) {
            objectOutputStream.writeObject(message);
        }
    }

    public ObjectOutputStream getObjectOutputStream() {
        return objectOutputStream;
    }

    public String getUserName() {
        return userName;
    }

    public synchronized boolean isNewMessage() {
        return newMessage;
    }
}
