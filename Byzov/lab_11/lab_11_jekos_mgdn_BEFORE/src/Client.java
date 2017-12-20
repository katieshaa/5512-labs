import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Client {
    private int serverPort = 7777;
    private String serverIp = "localhost";
    private String name = "Client";
    private DatagramSocket clientSocket;
    private InetAddress serverAdress;
    private byte[] buffer = new byte[256];

    public Client(String ip, int port) throws SocketException, UnknownHostException {
        this.serverIp = ip;
        this.serverPort = port;
        this.clientSocket = new DatagramSocket();
        this.serverAdress = InetAddress.getByName(this.serverIp);
    }

    public void run() {
        Thread sendThread = new Thread(new Runnable() {
            public void run() {
                while(true) {
                    while(true) {
                        while(true) {
                            try {
                                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                                String line = reader.readLine();
                                if (line.equals("@quit")) {
                                    System.exit(0);
                                }

                                if (line.length() > 5 && line.substring(0, 5).equals("@name")) {
                                    Client.this.name = line.substring(6, line.length());
                                    System.out.println("Имя изменено.");
                                } 

                                 else {

                                    DatagramPacket packet = new DatagramPacket(line.getBytes(), line.getBytes().length, Client.this.serverAdress, Client.this.serverPort);
                                    Client.this.clientSocket.send(packet);
                                }
                            } catch (IOException var6) {
                                var6.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
        sendThread.start();
        Thread receiveThread = new Thread(new Runnable() {
            public void run() {
                while(true) {
                    try {
                        DatagramPacket packet = new DatagramPacket(Client.this.buffer, Client.this.buffer.length);
                        Client.this.clientSocket.receive(packet);
                        String line = new String(packet.getData(), 0, packet.getLength());
                        System.out.println(line);
                    } catch (IOException var3) {
                        var3.printStackTrace();
                    }
                }
            }
        });
        receiveThread.start();
    }
}