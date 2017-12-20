import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {
    private int serverPort = 7777;
    private String serverIp = "localhost";
    private String name = "Client";
    private DatagramSocket clientSocket;
    private InetAddress serverAdress;
    private byte[] buffer = new byte[256];
    private ArrayList<String> msgDump = new ArrayList();

    public Client(String ip, int port) throws SocketException, UnknownHostException {
        this.serverIp = ip;
        this.serverPort = port;
        this.clientSocket = new DatagramSocket();
        this.serverAdress = InetAddress.getByName(this.serverIp);
    }

    public synchronized void addMsg(String msg) {
        this.msgDump.add(msg);
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
                                } else if (line.length() > 5 && line.substring(0, 5).equals("@dump")) {
                                    File file = new File(line.substring(6, line.length()));
                                    FileWriter writer = new FileWriter(file);

                                    for(int i = 0; i < Client.this.msgDump.size(); ++i) {
                                        writer.write((String)Client.this.msgDump.get(i) + "\n");
                                    }

                                    writer.close();
                                } else {
                                    line = Client.this.name + " : " + line;
                                    Client.this.addMsg(line);
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
                        Client.this.addMsg(line);
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