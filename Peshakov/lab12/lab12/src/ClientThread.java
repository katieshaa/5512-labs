

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeSet;

public class ClientThread extends Thread {
    private Socket socket;
    private PrintWriter out;
    private String userName = "User";
    private BufferedReader in;
    private final UserList users;
    private TreeSet<String> blackList;

    public ClientThread(Socket s, UserList userList) throws IOException {
        blackList = new TreeSet<>();
        users = userList;
        socket = s;
        System.out.println("client connected " + s.getPort());
        out = new PrintWriter(s.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
    }

    public void run()
    {
        try {
            String message;
            while(true) {
                message = in.readLine();
                if(message.equals("")) {
                    continue;
                }
                if(message.charAt(0) == '@') {
                    int spaceInd = message.indexOf(' ');
                    String command;
                    if(spaceInd == -1) {
                        command = message;
                    }
                    else {
                         command = message.substring(0, spaceInd);
                    }
                    if (command.equals("@name")) {
                        String name = message.substring(spaceInd + 1);
                        if (users.isOnline(name)) {
                            out.println("@fail");
                        }
                        else {
                            userName = name;
                        }
                    }
                    if (command.equals("@senduser")) {
                        String recipient = "";
                        int secondSpaceInd = message.indexOf(' ', spaceInd + 1);
                        recipient = message.substring(spaceInd + 1, secondSpaceInd);
                        message = message.substring(secondSpaceInd + 1, message.length());
                        sendMessage(userName + " :" + message, recipient);
                    }
                    if(command.equals("@alarm")) {
                        if(userName.equals("User")) {
                            out.println("You must choose name.");
                            continue;
                        }
                        try {
                            String dateString = message.substring(spaceInd + 1, message.length());
                            SimpleDateFormat dateFormat = new SimpleDateFormat("d M y k m");
                            Date date = dateFormat.parse(dateString);
                            users.setTimer(this, date);
                        }
                        catch (Exception ex) {
                            System.out.println("Wrong date.");
                            continue;
                        }
                    }
                    if(command.equals("@ignore")) {
                        blackList.add(message.substring(spaceInd + 1));
                    }
                    if(command.equals("@exit")) {
                        sendMessage("User " + userName + "left chat.");
                        users.remove(this);
                        socket.close();
                        synchronized (users) {
                            users.remove(this);
                        }
                        return;
                    }
                }
                else {
                    sendMessage(userName + ": " + message);
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        PrintWriter out;
        synchronized (users) {
            for (ClientThread clientThread : users.getList()) {
                if (clientThread == this || clientThread.isIgnore(this.getUsername())) {
                    continue;
                }
                out = clientThread.getWriter();
                out.println(message);
            }
        }
    }

    private void sendMessage(String message, String name) {
        PrintWriter out;
        synchronized (users) {
            for (ClientThread clientThread : users.getList()) {
                if (clientThread.getUsername().equals(name) && !clientThread.isIgnore(this.getUsername())) {
                    out = clientThread.getWriter();
                    out.println(message);
                    break;
                }
            }
        }
    }

    public boolean isIgnore(String name) {
        return blackList.contains(name);
    }

    public PrintWriter getWriter() {
        return out;
    }

    public BufferedReader getReader() {return in;}


    public String getUsername() {
        return userName;
    }

}
