import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Server {

    private DatagramSocket serverSocket;
    private int port = 0;
    private int clientPort = 0;
    private String name = null;
    private InetAddress IP = null;
    private boolean quit = false;

    public Server(int port) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        this.port = port;
        serverSocket = new DatagramSocket(this.port);
        byte[] receiveData = new byte[1024];
        System.out.println("Available command\n@name - set username\n@quit - exit");
        System.out.println("Wait client...");
        while(clientPort == 0 && IP == null) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            clientPort = receivePacket.getPort();
            IP = receivePacket.getAddress();
            String receiveMessage = new String(receivePacket.getData());
            System.out.println("\r" + receiveMessage);
        }
        WaitMessage thread = new WaitMessage("wait" , serverSocket);
        thread.start();
        while (!quit) {
            System.out.print("m: ");
            String message = in.readLine();
            if (message.contains("@")) {
                message = message.replaceAll("@", "");

                if (message.contains("name")) {
                    message = message.replaceFirst("name", "");
                    if (message.charAt(0) == ' ') {
                        message = message.replaceFirst(" ", "");
                    }
                    name = message;
                    System.out.println("You name: " + name);
                }
                if (message.contains("quit")) {
                    quit = true;
                }
            } else {
                if (name != null) {
                    String totalMessage = "@" + name + ": " + message;
                    byte[] sendData = totalMessage.getBytes();
                    DatagramPacket receivePacket = new DatagramPacket(sendData, sendData.length, IP, clientPort);
                    serverSocket.send(receivePacket);
                } else {
                    System.out.println("Message not sent\nEnter Name!");
                }
            }
        }
        thread.interrupt();
        System.out.println("End program");
        System.exit(0);
    }

    private class WaitMessage extends Thread {

        private byte[] receiveData;
        private DatagramSocket clientSocket;

        public WaitMessage(String name, DatagramSocket clientSocket) {
            super(name);
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                waitMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void waitMessage() throws IOException {
            while(!quit) {
                receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                port = receivePacket.getPort();
                IP = receivePacket.getAddress();
                String receiveMessage = new String(receivePacket.getData());
                System.out.print("\r" + receiveMessage + "\nm: ");
            }
        }
    }
}
