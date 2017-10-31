package Client;

import Console.ConsoleManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Куддус on 02.10.2017.
 */
public class Listener extends Thread {
    private Client client;
    private ConsoleManager console;
    private final int bufferSize = 1024;
    public Listener(Client c, ConsoleManager co)
    {
        client = c;
        console = co;
    }
    public void interrupt()
    {
        super.interrupt();
        client.close();
        try {
            console.close();
        }
        catch(IOException exception) {}

    }
    private DatagramPacket receivePacket() throws IOException {
        DatagramSocket sourceSocket = this.client.getDate();

        byte[] data = new byte[this.bufferSize];
        DatagramPacket packet = new DatagramPacket(data, data.length);

        sourceSocket.receive(packet);

        return packet;
    }


    private void handlerCommands(String command, DatagramPacket packet) throws IOException {
        if(command.equals("@newClient")) {
            this.client.setSocket(packet.getSocketAddress());
            this.console.printSystemMessage(packet.getAddress() + " connected.", null);
        }
    }


    @Override
    public void run() {
        try {
            while(true) {
                DatagramPacket packet = receivePacket();
                String message = this.console.getMessage(packet);

                if(message.startsWith("@")) {
                    handlerCommands(message, packet);
                }
                else {
                    console.write(message);
                }

            }
        }
        catch(IOException exception) {
            if(!this.client.isClosed()) {
                this.console.printSystemMessage("Error in listener.", exception);
            }
        }
    }

}
