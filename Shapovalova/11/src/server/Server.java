package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class Server {

    private DatagramSocket server;
    private int port = 8283;
    private int clientPort = 0;
    private String name = null;
    private InetAddress IP = null;
    private boolean exit = false;

    public Server(int port) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        this.port = port;
        
        server = new DatagramSocket(port);
        
        byte[] receiveData = new byte[1024];
        
        System.out.println("Wait client...");
        
        while(clientPort == 0 && IP == null) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            server.receive(receivePacket);
            clientPort = receivePacket.getPort();
            IP = receivePacket.getAddress();
            String receiveMessage = new String(receivePacket.getData());
            System.out.println("\r" + receiveMessage);
        }
        
        
        WaitMessage thread = new WaitMessage("wait" , server);
        thread.start();
        
        System.out.print("Enter name ");
        name = in.readLine();

        while (!exit) { 
            System.out.print("you: ");
            String message = in.readLine();
            
            if(message.contains("@name")) {
            	message = message.replaceAll("@name", "");
            	 if(message.charAt(0) == ' ') {
                     message = message.replaceFirst(" ", "");
                 }
            	 String lastname;
            	 lastname = name;
            	name = message;
            	message = lastname + " change name: " + name;
           }
               if (message.contains("@quit")) {
            	   exit = true;
                   message = "user left chat";
                }
              String allMessage = "@" + name + ": " + message;
              byte[] sendData = allMessage.getBytes();
              DatagramPacket receivePacket = new DatagramPacket(sendData, sendData.length, IP, clientPort);
              server.send(receivePacket);
        }
        
        thread.interrupt();
        System.out.println("You left chat!");
        System.exit(0);
    }

    private class WaitMessage extends Thread {

        private byte[] receiveData;
        private DatagramSocket clientSocket;

        public WaitMessage(String name, DatagramSocket clientSocket) {
            super(name);
            this.clientSocket = clientSocket;
        }

        public void run() {
            try {
                waitMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void waitMessage() throws IOException {
            while(!exit) {
                receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                port = receivePacket.getPort();
                IP = receivePacket.getAddress();
                String receiveMessage = new String(receivePacket.getData());
                System.out.print("\r" + receiveMessage + "\nyou: ");
               
            }
        }
    }
}