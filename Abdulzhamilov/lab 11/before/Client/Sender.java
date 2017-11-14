package Client;

import Console.ConsoleManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

/**
 * Created by Куддус on 02.10.2017.
 */
public class Sender extends Thread {
    private ConsoleManager console;
    private Client client;
    public Sender(Client c,ConsoleManager con)
    {
        client = c;
        console = con;
    }
    public void send(String message) throws IOException {
        DatagramSocket sourceSocket = this.client.getDate();
        SocketAddress destinationSocket = this.client.getSocket();

        byte[] data = message.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, destinationSocket);

        sourceSocket.send(packet);
    }


    public void sendMessage(String message) throws IOException {
        message = this.client.getName() + ": " + message;
        send(message);

        this.console.write(message);
    }
    public void command(String command)throws IOException
    {
        if (command.startsWith("@name"))
        {
            if((command.length() > 6) && (command.charAt(5) == ' ')) {
                String name = command.substring(6);
                if (!name.startsWith("SYSTEM")) {
                    client.setName(name);
                } else {
                    console.printSystemMessage("Name cant be starts with \"SYSTEM\".", null);
                }
            }
        }
        else if (command.startsWith("@newClient"))
        {
            if (command.equals("@newClient"))
            {
                send(command);
            }
            else
            {
                    console.printSystemMessage("WTF?!", null);
            }
        }
        else if(command.startsWith("@quit"))
        {
            if (command.length() == 5)
            {
                    this.client.getListener().interrupt();
                    interrupt();
            }
            else
            {
                    this.console.printSystemMessage("WTF?!", null);
            }
        }
        else
        {
                console.printSystemMessage("WTF?!", null);
        }
    }

 public void run()
 {

         try {
             while (true) {
                 String str = console.read();
                 str.trim();
                 if (str.startsWith("@")) {
                     command(str);
                 } else {
                     if (client.getSocket() != null) {
                         sendMessage(str);
                     } else {
                         console.printSystemMessage("No connections", null);
                     }

                 }
             }
         } catch (IOException exception) {
             if (!this.client.isClosed()) {
                 this.console.printSystemMessage("Error in sender.", exception);
             }
         }

 }
}
