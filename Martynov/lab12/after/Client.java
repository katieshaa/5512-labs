import java.net.*;
import java.io.*;
import java.util.*;
public class Client implements Runnable {
    private Socket socket;

    public Client(String address, int port) {

        try {
            this.socket = new Socket(InetAddress.getByName(address), port);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void run() {
        InputHandeler client = new InputHandeler(socket);
        System.out.println("Use \"@name %yourname%\" to define your username.\nType message and press enter to send it\n" +
                "Use @stop to exit programm");
        System.out.println("If you have log and password use @login.\nOr register by entering command @register");

        new Thread(client).start();

        Scanner scanner = new Scanner(System.in);

        while(true){
            String input = scanner.nextLine();

            if (input.equals("@stop")) {
                System.exit(0);
            }

            client.send(input);

        }

    }

    public class InputHandeler implements Runnable {
        private Socket socket;
        private PrintStream writer;

        InputHandeler(Socket socket) {

            this.socket = socket;

            try {
                writer = new PrintStream(socket.getOutputStream());
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        public void send(String text) {
            writer.println(text);
        }

        public void run() {
            try (BufferedReader socketInputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                while (true) {
                    String receivedString = receiveString(socketInputReader);


                    if (receivedString == null) {
                        close();
                        return;
                    }
                    System.out.println(receivedString);
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        void close() throws IOException {
            if (!socket.isClosed()) {
                socket.close();
            }

            System.exit(0);
        }

        private String receiveString(BufferedReader buff) throws IOException {
            String receivedString = null;

            try {
                receivedString = buff.readLine();
            } catch (SocketException e) {
                close();
                System.out.println(e);
                System.exit(0);
            }

            return receivedString;
        }

    }



    public static void main(String[] args) {
        Client client = new Client("localhost", 5001);
        new Thread(client).start();


    }

}