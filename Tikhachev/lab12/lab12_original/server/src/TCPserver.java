import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Created by root on 04.11.17 with love.
 */

public class TCPserver {

    private int portNumber;
    private ServerSocket serverSocket;
    private Vector<PrintWriter> allClientsOutputStream;
    private Vector<Socket> allClientsSocket;
    private HashMap<Socket, String> clientsName;

    public TCPserver(int port) {
        this.portNumber = port;
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        allClientsOutputStream = new Vector<>();
        allClientsSocket = new Vector<>();
        clientsName = new HashMap<>();
    }

    public void startServer() {
        try {
            SayTimeThread sayTimeThread = new SayTimeThread();
            sayTimeThread.start();
            while (true) {
                Thread.sleep(100);
                System.out.println("Wait connection");
                Socket clientSocket = serverSocket.accept();
                Identification thread = new Identification(clientSocket);
                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*************************************************************************************************************************************************/
    private class ClientThread extends Thread {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public ClientThread(Socket s) throws IOException {
            socket = s;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            synchronized (allClientsOutputStream) {
                allClientsOutputStream.add(out);
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String fromClient = in.readLine();
                    if (fromClient == null) {
                        synchronized (allClientsOutputStream) {
                            allClientsOutputStream.remove(out);
                        }
                        synchronized (allClientsSocket) {
                            allClientsSocket.remove(socket);
                        }
                        synchronized (clientsName) {
                            clientsName.remove(socket);
                        }
                        System.out.println("Client port:" + socket.getPort() + " disconnect");
                        break;
                    }
                    if (fromClient.charAt(0) == '@' && !fromClient.contains("name")) {
                        fromClient = fromClient.replaceFirst("@", "");
                        StringBuilder name = new StringBuilder();
                        char tmp = fromClient.charAt(0);
                        while (tmp != ' ') {
                            name.append(tmp);
                            fromClient = fromClient.replaceFirst(String.valueOf(tmp), "");
                            tmp = fromClient.charAt(0);
                        }
                        fromClient = fromClient.replaceFirst(String.valueOf(tmp), "");
                        if(name.toString().toLowerCase().equals("server")) {
                            if(fromClient.toLowerCase().contains("привет")) {
                                out.println("Server: " + clientsName.get(socket) + " Обнял, поднял, заплакал");
                            } else {
                                out.println("Server: " + fromClient);
                            }
                        } else {
                            Map.Entry<Socket, String> entry = null;
                            Iterator<Map.Entry<Socket, String>> it = clientsName.entrySet().iterator();
                            boolean send = false;
                            while (it.hasNext()) {
                                entry = it.next();
                                if (name.toString().equals(entry.getValue())) {
                                    PrintWriter tmpOut = new PrintWriter(entry.getKey().getOutputStream(), true);
                                    send = true;
                                    tmpOut.println(clientsName.get(socket) + ":" + fromClient);
                                }
                            }
                            if (!send) {
                                out.println("Client with name " + name.toString() + " not found. Message not sent");
                            }
                        }
                    } else {
                        for (PrintWriter anAllClientsOutputStream : allClientsOutputStream) {
                            if (anAllClientsOutputStream != out) {
                                anAllClientsOutputStream.println(clientsName.get(socket) + ":" + fromClient);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
/*************************************************************************************************************************************************/
    private class Identification extends Thread {
        private Socket socket;

        public Identification(Socket s) {
            socket = s;
        }

        @Override
        public void run() {
            synchronized (allClientsSocket) {
                allClientsSocket.add(socket);
            }
            System.out.println("Connection established. Port:" + socket.getPort());
            try {
                customerIdentification(socket);
                ClientThread thread = new ClientThread(socket);
                thread.start();
            } catch (ConnectException e) {
                System.out.println("Client disconnect");
                synchronized (allClientsSocket) {
                    allClientsSocket.remove(socket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void customerIdentification(Socket socket) throws IOException {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            while (true) {
                out.println("Server: Enter you name!");
                String fromClient = in.readLine();
                if (fromClient == null) {
                    throw new ConnectException("disconnect");
                }
                if (fromClient.charAt(0) == '@' && fromClient.contains("name")) {
                    fromClient = fromClient.replace("@name", "");
                    if (!fromClient.equals("")) {
                        if (fromClient.charAt(0) == ' ') {
                            fromClient = fromClient.replaceFirst(" ", "");
                        }
                        if (fromClient.equals("")) {
                            out.println("Server: Incorrect name!");
                        } else {
                            if(fromClient.toLowerCase().equals("server")) {
                                out.println("Server: invalid name");
                            } else {
                                synchronized (clientsName) {
                                    boolean use = false;
                                    Map.Entry<Socket, String> entry = null;
                                    Iterator<Map.Entry<Socket, String>> it = clientsName.entrySet().iterator();
                                    while (it.hasNext()) {
                                        entry = it.next();
                                        if (fromClient.equals(entry.getValue())) {
                                            out.println("Server: Name alreade use");
                                            use = true;
                                            break;
                                        }
                                    }
                                    if (!use) {
                                        clientsName.put(socket, fromClient);
                                        out.println("Server: The name is accept");
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        out.println("Server: Incorrect name!");
                    }
                } else {
                    out.println("Server: You name is not know, message will not be sent");
                }
            }
        }
    }
/*************************************************************************************************************************************************/
    private class SayTimeThread extends Thread {

    @Override
    public void run() {
        while(true) {
            try {
                Thread.sleep(15000);
                synchronized (allClientsOutputStream) {
                    for (int i = 0; i < allClientsOutputStream.size(); i++) {
                        PrintWriter tmp = allClientsOutputStream.get(i);
                        tmp.println(new Date().toString());
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
}
