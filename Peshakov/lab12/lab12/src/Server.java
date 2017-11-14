import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class Server
{
    private static final UserList users = new UserList();
    public static void main(String[] args) {
        if(args.length != 1) {
            throw new RuntimeException("1 argument required");
        }
        int portNumber = Integer.parseInt(args[0]);
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(portNumber);
            System.out.println("Server started");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientThread clientThread = new ClientThread(clientSocket, users);
                clientThread.start();
                users.add(clientThread);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}


