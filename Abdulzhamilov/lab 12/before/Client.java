import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Куддус on 22.10.2017.
 */
public class Client{
        private BufferedReader in;
        private PrintWriter out;
        private Socket socket;

        public Client()
        {
            Scanner input = new Scanner(System.in);
            System.out.println("IP Adresse (Localhost 127.0.0.1).");
            System.out.println("Format: xxx.xxx.xxx.xxx");
            String ip = input.nextLine();
            try
            {
                socket = new Socket(ip,7777);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter( socket.getOutputStream(),true);
                System.out.println("Name");
                out.println(input.nextLine());

                Sender send = new Sender(in);
                send.start();
                String str = "";
                while (!str.equals("@exit"))
                {
                    str = input.nextLine();
                    out.println(str);
                }
                send.SenderStop();
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
            finally
            {
                close();
            }

        }
        private void close()
        {
            try {
                in.close();
                out.close();
                socket.close();
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
        }

}


