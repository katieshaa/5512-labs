import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by root on 17.10.17 with love.
 */
public class Client {
    private String name = null;
    private boolean quit = false;

    public Client(int port) throws IOException {
        BufferedReader fromClient = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        byte[] sendData = new byte[1024];
        InetAddress IP = InetAddress.getByName("localhost");
        System.out.println("Available command\n@name - set username\n@quit - exit");
        WaitMessage thread = new WaitMessage("wait", clientSocket);
        thread.start();
        while(!quit) {
            System.out.print("m: ");
            String message = fromClient.readLine();
            if(message.contains("@")){
                message = message.replaceAll("@", "");

                if(message.contains("name")) {
                    message = message.replaceFirst("name", "");
                    if(message.charAt(0) == ' ') {
                        message = message.replaceFirst(" ", "");
                    }
                    name = message;
                    System.out.println("You name: " + name);
                }
                if(message.contains("quit")) {
                    quit = true;
                }
            } else {
                if(name != null) {
                    String totalMessage = "@" + name + ": " + message;
                    for(int i = 0; i < sendData.length; i++) {
                        sendData[i] = 0;
                    }
                    sendData = totalMessage.getBytes();
                    DatagramPacket datagramPacket = new DatagramPacket(sendData, sendData.length, IP, port);
                    clientSocket.send(datagramPacket);
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

        private byte[] responseData;
        private DatagramSocket clientSocket;

        public WaitMessage(String name, DatagramSocket clientSocket) {
            super(name);
            responseData = new byte[1024];
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
                responseData = new byte[1024];
                DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length);
                clientSocket.receive(responsePacket);
                String responseMessage = new String(responsePacket.getData());
                System.out.print("\r" + responseMessage + "\nm: ");
            }
        }
    }
}
