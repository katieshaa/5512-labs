import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class Server {
    private int serverPort = 7777;
    private DatagramSocket serverSocket;
    private boolean running = false;
    private byte[] bufferReceive = new byte[256];
    private byte[] bufferSend = new byte[256];
    private String name = "Server";
    private ArrayList<String> msgDump = new ArrayList();
    private InetAddress clientAdress;
    private int clientPort = 0;

    public Server(int port) throws SocketException {
        this.serverPort = port;
        this.serverSocket = new DatagramSocket(port);
    }

    public synchronized void addMsg(String msg) {
        this.msgDump.add(msg);
    }

    public void run() {
        Thread receiveThread = new Thread(new Runnable() {
            public void run() {
                while(true) {
                    try {
                        DatagramPacket packet = new DatagramPacket(Server.this.bufferReceive, Server.this.bufferReceive.length);
                        Server.this.serverSocket.receive(packet);
                        Server.this.clientAdress = packet.getAddress();
                        Server.this.clientPort = packet.getPort();
                        String str = new String(packet.getData(), 0, packet.getLength());
                        Server.this.addMsg(str);
                        System.out.println(str);
                    } catch (IOException var3) {
                        var3.printStackTrace();
                    }
                }
            }
        });
        receiveThread.start();
        Thread sendThread = new Thread(new Runnable() {
            public void run() {
                while(true) {
                    if (Server.this.clientAdress != null && Server.this.clientPort != 0) {
                        try {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                            String readLine = reader.readLine();
                            if (readLine.equals("@quit")) {
                                System.exit(0);
                            }

                            if (readLine.length() > 5 && readLine.substring(0, 5).equals("@name")) {
                                Server.this.name = readLine.substring(6, readLine.length());
                                System.out.println("Имя изменено.");
                            } else if (readLine.length() > 5 && readLine.substring(0, 5).equals("@dump")) {
                                File file = new File(readLine.substring(6, readLine.length()));
                                FileWriter writer = new FileWriter(file);

                                for(int i = 0; i < Server.this.msgDump.size(); ++i) {
                                    writer.write((String)Server.this.msgDump.get(i) + "\n");
                                }

                                writer.close();
                            } else {
                                readLine = Server.this.name + " : " + readLine;
                                Server.this.addMsg(readLine);
                                DatagramPacket packet = new DatagramPacket(readLine.getBytes(), readLine.getBytes().length, Server.this.clientAdress, Server.this.clientPort);
                                Server.this.serverSocket.send(packet);
                            }
                        } catch (IOException var6) {
                            var6.printStackTrace();
                        }
                    }
                }
            }
        });
        sendThread.start();
    }
}