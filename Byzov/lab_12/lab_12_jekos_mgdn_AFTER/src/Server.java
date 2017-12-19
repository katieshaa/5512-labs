import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server {
    private List<ClientThread> connections =  Collections.synchronizedList(new ArrayList<ClientThread>());
    private ServerSocket socket;
    private ArrayList<Person> personArrayList = new ArrayList<>();
    public  Server() {
        try {
            readList();

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
    private void readList() throws FileNotFoundException {
        Scanner in = new Scanner(new File("vedomost.txt"));
        while (in.hasNext()) {
            String line = in.nextLine();
            String[] arr = line.split(" ");
            String name = arr[0];
            int a1 = Integer.parseInt(arr[1]);
            int a2 = Integer.parseInt(arr[2]);
            int a3 = Integer.parseInt(arr[3]);
            int a4 = Integer.parseInt(arr[4]);
            int a5 = Integer.parseInt(arr[5]);

            ArrayList<Integer> personOtmetki = new ArrayList<>();
            personOtmetki.add(a1);
            personOtmetki.add(a2);
            personOtmetki.add(a3);
            personOtmetki.add(a4);
            personOtmetki.add(a5);
            personArrayList.add(new Person(name, personOtmetki));
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
                    if (str.startsWith("@show")) {
                        synchronized (personArrayList) {
                            for (Person person : personArrayList) {
                                out.println(person);
                            }
                        }
                        continue;
                    }

                    if (str.startsWith("@set")) {
                        String[] arr = str.split(" ");
                        String whoName = arr[1];
                        int n = Integer.parseInt(arr[2]);
                        int otmetka = Integer.parseInt(arr[3]);
                        synchronized (personArrayList) {
                            for (Person person : personArrayList) {
                                if (person.name.equals(whoName)) {
                                    person.otmetki.set(n - 1, otmetka);
                                    out.println("исправлено");
                                }
                            }
                        }
                        continue;
                    }
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
