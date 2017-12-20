import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;

/**
 * Created by root on 04.11.17 with love.
 */
public class TCPclient {
    private Socket socket;
    private int port;
    private BufferedReader serverIn;
    private PrintWriter serverOut;
    private BufferedReader clientIn;


    public TCPclient(int p) {
        port = p;
        try {
            socket = new Socket("localhost", port);
            serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            serverOut = new PrintWriter(socket.getOutputStream(), true);
            clientIn = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Connection created");
        } catch (IOException e) {
            System.out.println("Connection error");
        }
    }

    public void start() {
        if (socket == null) {
            System.out.println("Connection not created");
            return;
        }
        String fromUser;
        boolean run = true;
        try {
            WaitMessage waitMessage = new WaitMessage(serverIn, socket);
            waitMessage.start();
            while (run) {
                fromUser = clientIn.readLine();
                if (fromUser.equals("@exit")) {
                    run = false;
                    System.exit(0);
                } else {
                    serverOut.println(fromUser);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class WaitMessage extends Thread {
        private BufferedReader in;
        private Socket socket;

        public WaitMessage(BufferedReader in, Socket s) {
            this.in = in;
            socket = s;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String fromServer = in.readLine();
                    if(fromServer == null) {
                        System.out.println("Connection with server reset");
                        while(true) {
                            try {
                                System.out.println("Try reconnect");
                                socket = new Socket("localhost", port);
                                serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                in = serverIn;
                                serverOut = new PrintWriter(socket.getOutputStream(), true);
                                System.out.println("Connection created");
                                break;
                            } catch (Exception e) {
                                try {
                                    Thread.sleep(9000);
                                } catch (InterruptedException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    } else {
                        System.out.println(fromServer);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
