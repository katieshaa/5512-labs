import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private BufferedReader keyboard;

    private Client(String adr, int port) throws IOException {
        InetAddress ipAddress = InetAddress.getByName(adr);
        socket = new Socket(ipAddress, port);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        keyboard = new BufferedReader(new InputStreamReader(System.in));
        Thread listener = new Thread(new FromServer());
        listener.start();
    }

    public static void main(String[] args) throws IOException {
        int port = 5665;
        new Client("localhost", port).run();
    }

    private void socketClose() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void run() {
        System.out.print("@name ");
        String name = new Scanner(System.in).nextLine();
        try {
            out.writeUTF(name);
            while (true) {
                String line;
                line = keyboard.readLine();
                if (socket.isClosed())
                    break;
                out.writeUTF(line);
                out.flush();
                if (line.equals("@quit")) {
                    socketClose();
                    break;
                }
            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

    private class FromServer implements Runnable {

        public void run() {
            try {
                String serverName = in.readUTF();
                while (true) {
                    String line;
                    line = in.readUTF();
                    if (line.equals("@quit")) {
                        System.out.println("server is quited");
                        break;
                    }
                    System.out.println(serverName + ": " + line);
                }
            } catch (IOException e) {
                socketClose();
            } finally {
                socketClose();
            }
        }
    }

}
