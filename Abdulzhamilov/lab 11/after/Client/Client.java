package Client;
import java.io.BufferedReader;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import Console.ConsoleManager;

import javax.swing.*;

/**
 * Created by Куддус on 02.10.2017.
 */
public class Client {
    private String name;
    private DatagramSocket date;
    private SocketAddress socked;
    private Sender sender;
    private JTextArea jTextArea1;
    private Listener listener;
    private File buffer;

    public Client(String n, DatagramSocket s, SocketAddress a, JTextArea tmp,File buf)
    {
        name = n;
        date = s;
        jTextArea1 = tmp;
        socked = a;
        buffer = buf;
    }
    public Client(){}
    public void close()
    {
        date.close();
    }
    public boolean isClosed()
    {
        return date.isClosed();
    }
    public void setName(String tmp)
    {
        name = tmp;
    }
    public String getName()
    {
        return name;
    }
    public DatagramSocket getDate()
    {
        return date;
    }
    public SocketAddress getSocket()
    {
        return socked;
    }
    public void setSocket(SocketAddress tmp)
    {
        socked = tmp;
    }
    public Listener getListener()
    {
        return listener;
    }


    public Sender getSender()
    {
        return sender;
    }

    public void messaging()throws Exception
    {
        ConsoleManager tmp = new ConsoleManager(jTextArea1,buffer);
        listener = new Listener(this,tmp);
        sender = new Sender(this,tmp);
        listener.start();
        sender.start();
    }
}
