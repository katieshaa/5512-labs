import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

/**
 * Created by root on 17.10.17 with love.
 */
public class Server {

    private DatagramSocket serverSocket;
    private int port = 0;
    private int clientPort = 0;
    private String name = null;
    private InetAddress IP = null;
    private boolean quit = false;

    public Server(int port) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        this.port = port;
        serverSocket = new DatagramSocket(this.port);
        byte[] receiveData = new byte[1024];
        System.out.println("Available command\n@name - set username\n@quit - exit");
        System.out.println("Wait client...");
        while(clientPort == 0 && IP == null) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            clientPort = receivePacket.getPort();
            IP = receivePacket.getAddress();
            String receiveMessage = new String(receivePacket.getData());
            System.out.println("\r" + receiveMessage);
        }
        WaitMessage thread = new WaitMessage("wait" , serverSocket);
        thread.start();
        while (!quit) {
            System.out.print("m: ");
            String message = in.readLine();
            if (message.contains("@")) {
                message = message.replaceAll("@", "");

                if (message.contains("name")) {
                    message = message.replaceFirst("name", "");
                    if (message.charAt(0) == ' ') {
                        message = message.replaceFirst(" ", "");
                    }
                    name = message;
                    System.out.println("You name: " + name);
                }
                if (message.contains("quit")) {
                    quit = true;
                }
            } else {
                if (name != null) {
                    sendMessage(message);
                } else {
                    System.out.println("Message not sent\nEnter Name!");
                }
            }
        }
        thread.interrupt();
        System.out.println("End program");
        System.exit(0);
    }

    private void guessing() throws IOException {
        String response;
        int lower = 0;
        int upper = 0;
        int enigma;
        int guess;
        sendMessage("Enter the lower limit.");
        response = waitMessage();
        try {
            lower = Integer.parseInt(response);
        } catch (NumberFormatException e) {
            sendMessage("Wrong number format. Try again.");
            guessing();
        }
        if(lower == 0) {
            sendMessage("Wrong format. Try again.");
            guessing();
        } else {
            sendMessage("Enter the upper limit.");
            response = waitMessage();
            upper = Integer.parseInt(response);
            if(upper == 0) {
                sendMessage("Wrong format. Try again.");
                guessing();
            } else {
                Random random = new Random(System.currentTimeMillis());
                enigma = random.nextInt(upper - lower + 1) + lower;
                sendMessage("The number is conceived.   Example:\n>4\n<5\n=7");
                sendMessage("Enigma == " + enigma);
                guess = enigma - 1;
                while(true) {
                    response = waitMessage();
                    if(check(response)) {
                        if(response.charAt(0) == '>') {
                            response = response.replaceAll(">", "");
                            guess = Integer.parseInt(response);
                            if(guess < enigma) {
                                sendMessage("True");
                            } else {
                                sendMessage("False");
                            }
                        }
                        if(response.charAt(0) == '<') {
                            response = response.replaceAll("<", "");
                            guess = Integer.parseInt(response);
                            if(guess > enigma) {
                                sendMessage("True");
                            } else {
                                sendMessage("False");
                            }
                        }
                        if(response.charAt(0) == '=') {
                            response = response.replaceAll("=", "");
                            guess = Integer.parseInt(response);
                            if(guess == enigma) {
                                sendMessage("Congratulations! You guessed the number.");
                                break;
                            } else {
                                sendMessage("False");
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean check(String response) throws IOException {
        if(!response.contains(">") && !response.contains("<") && !response.contains("=")) {
            sendMessage("Wrong format. Try again.");
            return false;
        } else {
            if(response.contains(">") && response.contains("<")) {
                sendMessage("Wrong format. Try again.");
                return false;
            } else {
                if(response.contains(">") && response.contains("=")) {
                    sendMessage("Wrong format. Try again.");
                    return false;
                } else {
                    if(response.contains("<") && response.contains("=")) {
                        sendMessage("Wrong format. Try again.");
                        return false;
                    } else {
                        if((response.charAt(0) != '>') && (response.charAt(0) != '<') && (response.charAt(0) != '=')) {
                            sendMessage("Wrong format. Try again.");
                            return false;
                        } else {
                            for(int i = 1; i < response.length(); i++) {
                                if((response.charAt(i) == '>') || (response.charAt(i) == '<') || (response.charAt(i) == '=')) {
                                    sendMessage("Wrong format. Try again.");
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private void sendMessage(String message) throws IOException {
        String totalMessage = "@" + name + ": " + message;
        byte[] sendData = totalMessage.getBytes();
        DatagramPacket receivePacket = new DatagramPacket(sendData, sendData.length, IP, clientPort);
        serverSocket.send(receivePacket);
    }

    private String waitMessage() throws IOException {
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket);
        port = receivePacket.getPort();
        IP = receivePacket.getAddress();
        String response = new String(receivePacket.getData());
        StringBuilder result = new StringBuilder();
        char tmp = '~';
        for (int i = 0; i < response.length(); i++) {
            while (tmp != ' ') {
                tmp = response.charAt(i);
                i++;
            }
            if (response.charAt(i) != '\u0000') {
                result.append(response.charAt(i));
            }
        }
        return result.toString();
    }

    private class WaitMessage extends Thread {

        private byte[] receiveData;
        private DatagramSocket clientSocket;

        public WaitMessage(String name, DatagramSocket clientSocket) {
            super(name);
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
                receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                port = receivePacket.getPort();
                IP = receivePacket.getAddress();
                String receiveMessage = new String(receivePacket.getData());
                if(receiveMessage.contains("start game")) {
                    guessing();
                }
                System.out.print("\r" + receiveMessage + "\nm: ");
            }
        }
    }
}
