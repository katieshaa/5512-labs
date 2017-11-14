package Server;

import Client.Client;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.DatagramSocket;

/**
 * Created by Куддус on 02.10.2017.
 */
public class Server {
    private Client client;
    public Server(String name, DatagramSocket date, JTextArea jTextArea1, File buf)
    {
        client = new Client(name,date,null, jTextArea1,buf);
    }
    public Server(){}
    public void messaging()throws Exception
    {
        this.client.messaging();
    }

}
