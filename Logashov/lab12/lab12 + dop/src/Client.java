import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by denis on 10.10.2017.
 */
public class Client {
    public static void main(String[] args) throws IOException {
        String hostName = args[0]; int portNumber = Integer.parseInt(args[1]);
        System.out.println("client started");
        try {
            Socket fromServer = new Socket(hostName, portNumber);
            PrintWriter out = new PrintWriter(fromServer.getOutputStream(), true);
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromUser;

            Stream str = new Stream(fromServer);
            str.start();

            while (true)
            {
                fromUser = stdIn.readLine();
                if (fromUser != null)
                {
                    out.println(fromUser);
                }
            }
        }
        catch (UnknownHostException e) {  }
        catch (IOException e) {  }
    }
    static class Stream extends Thread
    {
        private BufferedReader in;
        Stream(Socket socket)  throws IOException
        {
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
}