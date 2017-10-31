import javafx.util.Pair;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;



/*
Задание 12. Многопользовательский чат.

        Написать текстовый многопользовательский чат.
        Пользователь управляет клиентом. На сервере пользователя нет. Сервер занимается пересылкой сообщений между клиентами.
        По умолчанию сообщение посылается всем участникам чата.
        Есть команда послать сообщение конкретному пользователю (@senduser Vasya).
        Программа работает по протоколу TCP.
*/
/**
 * Created by denis on 10.10.2017.
 */
public class Server {
    public static void main(String[] args) throws IOException {
        int portNumber = Integer.parseInt(args[0]);
        ServerSocket serverSocket;
        ArrayList<clientThread> users = new ArrayList<>();
        serverSocket = new ServerSocket(portNumber);
        System.out.println("Server started");
        class AcceptThread extends Thread {
            public void run() {
                while (true) {
                    try {
                        Socket clientSocket = serverSocket.accept();
                        clientThread cliThread = new clientThread(clientSocket);
                        synchronized (users)
                        {
                            users.add(cliThread);
                        }
                        cliThread.start();
                    } catch (IOException ioe) {
                    }
                }
            }
        }
        AcceptThread at = new AcceptThread();
        at.start();
        while (true)
        {
            for (clientThread ct : users)
            {
                StringBuilder tmp = new StringBuilder();
                if (ct.newMessage)
                {
                        if (ct.input.contains("@senduser"))
                        {
                            String[] s = ct.input.split(" ");
                            for (clientThread client : users)
                            {
                                if (s[1].equals(client.name))
                                {
                                    tmp = new StringBuilder();
                                    for (int i = 2; i < s.length; i++)
                                    {
                                        tmp.append(s[i] + ' ');
                                    }
                                    client.send(ct.name + ":" + tmp.toString());
                                }
                            }
                        } else
                            {
                            for (clientThread client : users)
                            {
                                if (!ct.equals(client))
                                    client.send(ct.name + ": " + ct.input);
                            }
                        }
                    ct.newMessage = false;
                }
            }
        }
    }

    static class clientThread extends Thread {
        Socket socket;
        PrintWriter out;
        String name = "user";
        BufferedReader in;
        String input;
        boolean newMessage = false;

        clientThread(Socket s) throws IOException {
            socket = s;
            System.out.println("client connected " + s.getPort());
            out = new PrintWriter(s.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        }


        public void run() {
            try {
                while (true) {
                    input = in.readLine();
                    if (input.contains("@name")) {
                        setName();
                    } else {
                        newMessage = true;

                    }
                    System.out.println("ins: " + input);
                    //out.println("Server Says : " + clientCommand);
                }
            } catch (Exception e) {

            }
        }

        public void send(String message) {
            out.println(message);
        }

        private void setName()
        {
            String[] tmp = input.split(" ");
            name = tmp[1];
        }
    }
}