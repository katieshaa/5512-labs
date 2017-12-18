import java.net.*;
import java.io.*;
import java.util.*;
public class Server implements Runnable{

    private ServerSocket socket;
    private int connectionsNumber = 0;
    private HashMap<String, Socket> names;

    public Server(int port) {
        try{

            socket = new ServerSocket(port);

            names = new HashMap<>();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run() {
        while (true) {
            try {
                if(socket.isClosed()) {
                    break;
                }

                Socket tempSocket = socket.accept();
                names.put("user" + Integer.toString(connectionsNumber), tempSocket);

                new Thread(new ClientHandler(tempSocket, connectionsNumber)).start();
                connectionsNumber++;
                System.out.println("Connected " + (connectionsNumber-1));

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.exit(0);
    }

    public class ClientHandler implements Runnable {
        private Socket clientSocket;
        private String name;

        ClientHandler(Socket socket, int num) {
            this.clientSocket = socket;
            this.name = "user" + Integer.toString(num);
            names.put(name, clientSocket);
        }

        private void removeSelf() throws IOException {
            clientSocket.close();
            names.remove(name);
        }

        public void run() {
            try (BufferedReader socketInputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                String recievedString;

                while (true) {
                    recievedString = socketInputReader.readLine();

                    if (recievedString ==  null) {
                        removeSelf();
                        break;
                    }

                    handleInput(recievedString);
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        private void handleInput(String receivedString) throws IOException {
            if (receivedString.startsWith("@")) {
                command(receivedString);
            } else {
                    for (Map.Entry<String, Socket> entry : names.entrySet()) {
                        send(entry.getValue(), name + ":" + receivedString);
                    }
            }
        }

        private void command(String receivedString) throws IOException {
            if (receivedString.equals("@stop")) {
                removeSelf();
            } else if (!receivedString.contains(" ")) {
                send(clientSocket, ">>>Invalid command");
            } else if (receivedString.startsWith("@name")) {
                String newName = receivedString.substring(6, receivedString.length());
                if (names.containsKey(newName)) {
                    send(clientSocket, ">>>Your desired name is taken");
                } else {

                    names.remove(name);
                    names.put(newName, clientSocket);
                    name = newName;
                    send(clientSocket, ">>>Your name is now " + name);
                }
            } else {

                int firstSpace = receivedString.indexOf(' ');
                String targetName = receivedString.substring(1, firstSpace);

                if (!names.containsKey(targetName)) {
                    send(clientSocket, ">>>No user with such name found");
                } else {
                    send(names.get(targetName), "[private]" + name + ":" + receivedString.substring(firstSpace + 1,
                            receivedString.length()));
                    send(clientSocket, String.format("[to %s] : %s", targetName, receivedString.substring(firstSpace + 1,
                            receivedString.length())));
                }
            }
            }

        private void send(Socket socket, String string) throws IOException {
            if (!socket.isClosed()) {
                PrintStream socketOutputWriter = new PrintStream(socket.getOutputStream());

                socketOutputWriter.println(string);
            }
        }

    }

    public static void main(String[] args) {
        Server serverObj = new Server(5001);
        new Thread(serverObj).start();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.nextLine();
            if(input.equals("@stop")) {
                serverObj.close();
                break;
            }
        }

    }
}
