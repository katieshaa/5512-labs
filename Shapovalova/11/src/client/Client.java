package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Client {
    private String name = null;
    private boolean exit = false;

    public Client(int port) throws IOException {
        BufferedReader fromClient = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        
        byte[] sendData = new byte[1024];
        InetAddress IP = InetAddress.getByName("localhost");
        WaitMessage thread = new WaitMessage("wait", clientSocket);
        thread.start();
        
        System.out.print("Enter name ");
        name = fromClient.readLine();
        while(!exit) {
            System.out.print("you: ");
            String message = fromClient.readLine();
            
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
            
            if(message.contains("@quit")) {
                 exit = true;
                 message = "user left chat";
            }
            
            String totalMessage = "@" + name + ": " + message;
            for(int i = 0; i < sendData.length; i++) {
                 sendData[i] = 0;
            }
            sendData = totalMessage.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(sendData, sendData.length, IP, port);
            clientSocket.send(datagramPacket);
        }
        
        thread.interrupt();
        System.out.println("You left chat!");
        System.exit(0);
    }

    private class WaitMessage extends Thread {
        private byte[] responseData;
        private DatagramSocket clientSocket;

        public WaitMessage(String name, DatagramSocket clientSocket) {
            super(name);
            responseData = new byte[1024];
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
                responseData = new byte[1024];
                DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length);
                clientSocket.receive(responsePacket);
                String responseMessage = new String(responsePacket.getData());
                System.out.print("\r"+responseMessage + "\nyou: ");
            }
        }
    }
} 