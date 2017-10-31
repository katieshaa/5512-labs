import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class clientThread extends Thread {
    Socket socket;
    public PrintWriter out;
    public String name = "user";
    public BufferedReader in;
    public String input;
    public boolean newMessage = false;
    clientThread(Socket s) throws IOException
    {
        socket = s;
        System.out.println("client connected " + s.getPort());
        out = new PrintWriter(s.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }
    public void run()
    {
        try
        {
            while(true)
            {
                input = in.readLine();
                if(input.contains("@name"))
                {
                    setName();
                }
                else
                {
                    newMessage = true;

                }
                System.out.println("ins: " + input);
                //out.println("Server Says : " + clientCommand);
            }
        }
        catch(Exception e)
        {

        }
    }
    public void send(String message)
    {
        out.println(message);
    }
    private void setName()
    {
        String[] tmp = input.split(" ");
        StringBuilder temp = new StringBuilder();
        name = tmp[1];
    }
}