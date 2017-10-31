package Server;

import Client.Client;

import java.net.DatagramSocket;

/**
 * Created by Куддус on 02.10.2017.
 */
public class Server {
    private Client client;
    public Server(String name, DatagramSocket date)
    {
        client = new Client(name,date,null);
    }

    public void messaging()
    {
        this.client.messaging();
    }

}
