import java.io.IOException;
import java.net.DatagramSocket;
import  Server.Server;
/**
 * Created by Куддус on 02.10.2017.
 */
public class MainServer {
    public static void main(String [] arg){
        if (arg.length != 1)
        {
            System.out.println("Error");
        }
        else
        {
            int port = Integer.valueOf(arg[0]);
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket(port);
            }
            catch (IOException e)
            {
                System.out.println("Error 404");
            }
            Server server = new Server("Server",socket);
            server.messaging();
        }

    }
}
