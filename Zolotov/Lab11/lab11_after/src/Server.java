import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Server {
    private ServerSocket ss;
    private Socket socket;
    private String name;
    private DataOutputStream out;
    private BufferedReader keyboard;
    private Thread send;

    public Server(int port) throws IOException {
        ss = new ServerSocket(port);
    }

    public static void main(String[] args) throws IOException {
        int port = 5665;
        new Server(port).run(); //порт задаёт пользователь
    }

    public void run() {
        System.out.print("@name ");
        name = new Scanner(System.in).nextLine();
        try {
            socket = ss.accept();
            DataInputStream in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            send = new Thread(new Send());
            send.start();
            String clientName = in.readUTF();
            try {
                while (true) {
                    String line;
                    line = in.readUTF();
                    if (line.equals("@quit")) {
                        System.out.println("client is quited");
                        break;
                    }
                    System.out.println(clientName + ": " + line);
                }
            } catch (SocketException e) {
                closing();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closing();
        }
    }

    private void closing() {
        try {
            ss.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Send implements Runnable {

        Send() {
            keyboard = new BufferedReader(new InputStreamReader(System.in));
        }

        public void run() {
            try {
                out.writeUTF(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (!ss.isClosed()) {
                String line;
                try {
                    line = keyboard.readLine();
                    if (line != null && !socket.isClosed()) {
                        out.writeUTF(line);
                        out.flush();
                        if (line.equals("@quit")) {
                            closing();
                            break;
                        }
                    }
                } catch (Exception e) {
                    if ("Socket closed".equals(e.getMessage())) {
                        closing();
                        break;
                    }
                    e.printStackTrace();
                    closing();
                }
            }
        }
    }
}
