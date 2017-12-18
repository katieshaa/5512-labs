/**
 * Created by root on 04.11.17 with love.
 */
public class Main {
    public static void main(String[] args) {
        int port = 777;
        TCPserver tcpServer = new TCPserver(port);
        tcpServer.startServer();
    }
}
