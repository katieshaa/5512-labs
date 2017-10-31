/**
 * Created by Куддус on 22.10.2017.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
public class Server {
    private List<ClientThread> connections =  Collections.synchronizedList(new ArrayList<ClientThread>());
    private ServerSocket socket;
    public  Server() {
        try {
            socket = new ServerSocket(7777);
            while (true) {
                Socket client = socket.accept();
                ClientThread c1 = new ClientThread(client,connections);
                connections.add(c1);
                c1.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            closAll();
        }
    }
    private void closAll() {
        try {
            socket.close();
            synchronized (connections) {
                Iterator<ClientThread> iter = connections.iterator();
                while (iter.hasNext()) {
                    ((ClientThread) iter.next()).close();
                }
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }

    }
    public class ClientThread extends Thread {
        private String name  = "";
        private BufferedReader in;
        private PrintWriter out;
        private Socket socket;
        private  List<ClientThread> connections;
        public ClientThread(Socket sc, List<ClientThread> list)
        {
            socket = sc;
            connections = list;
            try
            {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter( socket.getOutputStream(),true);
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
                close();
            }
        }
        public String getNameClient()
        {
            return this.name;
        }
        public void close()
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
        public  void run()
        {
            try {
                name = in.readLine();
                synchronized (connections) {
                    Iterator<ClientThread> iter = connections.iterator();
                    while (iter.hasNext()) {
                        ((ClientThread) iter.next()).out.println(name + " connect");
                    }
                }
                String str = "";
                while (true)
                {
                    str = in.readLine();
                    if (str.startsWith("@exit"))
                        break;
                    if (str.startsWith("@senduser"))
                    {
                        String [] st = str.split(" ");


                        synchronized (connections) {
                            ClientThread iter  = connections.get(0);
                            String client = iter.getNameClient();
                            if ( client.equals(st[1]))
                            {
                                String message = str.substring(10  + st[1].length());
                                iter.out.println(name + ": " + message);
                                continue;
                            }
                           for (int i = 0;i < connections.size() ;i++)
                                iter  = connections.get(i);
                                client = iter.getNameClient();
                                if ( client.equals(st[1]))
                                {
                                    String message = str.substring(10  + st[1].length());
                                    iter.out.println(name + ": " + message);
                                    continue;
                                }



                            }

                    }
                    synchronized (connections) {
                        Iterator<ClientThread> iter = connections.iterator();
                        while (iter.hasNext()) {
                            ((ClientThread) iter.next()).out.println(name + ": " + str);
                        }
                    }
                }
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
            }
            finally {
                close();
            }
        }
        
    }
}
