package Client;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import Console.ConsoleManager;

/**
 * Created by Куддус on 02.10.2017.
 */
public class Client {
    private String name;
    private DatagramSocket date;
    private SocketAddress socked;
    private Sender sender;
    private Listener listener;

    public Client(String n, DatagramSocket s,SocketAddress a)
    {
        name = n;
        date = s;
        socked = a;
    }
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

    public void messaging()
    {
        ConsoleManager tmp = new ConsoleManager();
        listener = new Listener(this,tmp);
        sender = new Sender(this,tmp);
        listener.start();
        sender.start();
    }
}
