import Client.Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * Created by Куддус on 02.10.2017.
 */
public class MainClient {
    public static void main(String[] arg)
    {
        if (arg.length != 2)
        {
            System.out.println("Error");
        }
        else
        {
            int port = Integer.valueOf(arg[1]);
            InetSocketAddress destinationSocket = null;
            DatagramSocket socket = null;
            try {
                destinationSocket = new InetSocketAddress(arg[0],port);
                socket = new DatagramSocket();
            }
            catch (Exception e)
            {
                System.out.println("Error 404");
            }
            Client client = new Client("Client",socket,destinationSocket);
            try {
                byte[] data = "@newClient".getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, destinationSocket);
                socket.send(packet);
            }
            catch(IOException exception) {
                System.out.println("SYSTEM: Error connection.");
            }
            client.messaging();

        }
    }
}
