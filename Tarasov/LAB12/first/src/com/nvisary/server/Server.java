package com.nvisary.server;

import com.nvisary.dop.AlarmTask;
import com.nvisary.util.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Server {
    private ServerSocket serverSocket;
    private Socket socket;
    private ArrayList<Connection> connections = new ArrayList<>();
    private ArrayList<String> names = new ArrayList<>();
    private Server server = this;

    public Server(int serverPort) throws IOException {
        serverSocket = new ServerSocket(serverPort);
        System.out.println("server started");
    }

    public void start() throws IOException, InterruptedException, ClassNotFoundException {
        Thread acceptUsersTh = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        socket = serverSocket.accept();
                        Connection connection = new Connection(socket, server);
                        connection.start();
                        addConnection(connection);
                        System.out.println("client: " + connection.getIp() + " connected.");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        Thread resendTh = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        int size = getConnectionsSize();
                        for (int i = 0; i < size; i++) {
                            Connection connection = getConnection(i);
                            if (connection.isNewMessage()) {
                                Message message = connection.getOutputMessage();
                                
                                if (message.getDestinationUser().equals("null")) {
                                    sendMessageToAll(message);
                                } else {
                                    sendMessageToUser(message);
                                }

                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        acceptUsersTh.start();
        resendTh.start();
    }

    private synchronized void sendMessageToAll(Message message) throws IOException {
        synchronized (connections) {
            for (Connection connection : connections) {
                connection.sendMessageToUser(message);
            }
        }
    }

    public void addNewUserName(String userName) {
        names.add(userName);
    }

    public boolean nameIsFree(String userName) {
        for (String name : names) {
            if (name.equals(userName)) {
                return false;
            }
        }
        return true;
    }

    private synchronized void sendMessageToUser(Message message) throws IOException {
        synchronized (connections) {
            for (Connection connection : connections) {
                if (connection.getUserName().equals(message.getDestinationUser())) {
                    connection.sendMessageToUser(message);
                }
            }
        }
    }

    private synchronized int getConnectionsSize() {
        return connections.size();
    }

    private Connection getConnection(int index) {
        return connections.get(index);
    }

    private synchronized void addConnection(Connection connection) {
        connections.add(connection);
    }
}
