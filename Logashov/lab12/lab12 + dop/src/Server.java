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
    static private TreeMap<String, String> library;
    public static void main(String[] args) throws IOException {
        int portNumber = Integer.parseInt(args[0]);
        ServerSocket serverSocket;
        ArrayList<clientThread> users = new ArrayList<>();
        serverSocket = new ServerSocket(portNumber);
        System.out.println("Server started");
        library = new TreeMap<>();
        loadLibrary();
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
                    if(ct.input.contains("@list"))
                    {
                       tmp = new StringBuilder();
                        for(String s: library.keySet())
                        {
                            tmp.append(s + " : " + library.get(s) + '\n');
                        }
                        ct.send(tmp.toString());
                    }
                    else if(ct.input.contains("@take"))
                    {
                        String[] s = ct.input.split(" ");
                        StringBuilder sb = new StringBuilder();
                        for(int i = 1; i < s.length; i++)
                        {
                            sb.append(s[i]);
                            if(i != s.length - 1)
                            {
                                sb.append(' ');
                            }
                        }
                        if(giveBook(sb.toString(), ct.name))
                        {
                            ct.send("you took the book: " + sb.toString());
                        }
                        else
                        {
                            ct.send("There is no book or it is taken");
                        }
                    }
                    else if (ct.input.contains("@return"))
                    {
                        String[] s = ct.input.split(" ");
                        StringBuilder sb = new StringBuilder();
                        for(int i = 1; i < s.length; i++)
                        {
                            sb.append(s[i]);
                            if(i != s.length - 1)
                            {
                                sb.append(' ');
                            }
                        }
                        if(takeBook(sb.toString(), ct.name))
                        {
                            ct.send("you return book: " + sb.toString());
                        }
                        else
                        {
                            ct.send("book not found");
                        }
                    }
                    else {
                        if (ct.input.contains("@senduser")) {
                            String[] s = ct.input.split(" ");
                            for (clientThread client : users) {
                                if (s[1].equals(client.name)) {
                                    tmp = new StringBuilder();
                                    for (int i = 2; i < s.length; i++) {
                                        tmp.append(s[i] + ' ');
                                    }
                                    client.send(ct.name + ":" + tmp.toString());
                                }
                            }
                        } else {
                            for (clientThread client : users) {
                                if (!ct.equals(client))
                                    client.send(ct.name + ": " + ct.input);
                            }
                        }
                    }
                    ct.newMessage = false;
                }
            }
        }
    }

    static private void loadLibrary() throws IOException
    {
        Scanner sc = new Scanner(new File("library.txt"));
        while(sc.hasNextLine())
        {
            library.put(sc.nextLine(), "noone");
        }
    }

    static private boolean giveBook(String bookname, String name)
    {
        if(library.containsKey(bookname))
        {
            if(library.get(bookname).equals("noone"))
            {
                library.put(bookname, name);
                return true;
            }
            else
            System.out.println("find");
        }
        return false;
    }
    static private boolean takeBook(String bookname, String name)
    {
        if(library.containsKey(bookname))
        {
            if(library.get(bookname).equals(name))
            {
                library.put(bookname, "noone");
                return true;
            }
            System.out.println("find");
        }
        return false;
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