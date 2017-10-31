import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

public class Stream extends Thread
{
    private Socket socket;
    private BufferedReader in;
    Stream(Socket socket)  throws IOException
    {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void run()
    {
        String message;
        try
        {
            while ((message = in.readLine()) != null)
            {
                System.out.println(message);
            }
        }
        catch(IOException e)
        {

        }
    }
}