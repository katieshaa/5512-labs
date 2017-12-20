import java.io.*;
import java.net.Socket;


public class ClientThread extends Thread {
    private Socket socket;
    private PrintWriter out;
    private String userName = "User";
    private BufferedReader in;
    private final UserList users;
    private boolean isAdmin;
    private boolean isBanned;

    public ClientThread(Socket s, UserList userList) throws IOException {
        socket = s;
        System.out.println("client connected " + s.getPort());
        out = new PrintWriter(s.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        users = userList;
        isAdmin = false;
        isBanned = false;
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
                        String recipient;
                        int secondSpaceInd = 0;
                        secondSpaceInd = message.indexOf(' ', spaceInd + 1);
                        if(secondSpaceInd == 0)
                            System.out.println("your message is empty");
                        else {
                            recipient = message.substring(spaceInd + 1, secondSpaceInd);
                            message = message.substring(secondSpaceInd + 1, message.length());
                            sendMessage("whisper from " + userName + ": " + message, recipient);
                        }

                    }

                    if (command.equals("@setAdmin")) {
                        setAdmin();
                    }
                    if (command.equals("@ban")) {
                        String me;
                        me = message.substring(spaceInd + 1, message.length());
                        ban(me);
                    }
                    if (command.equals("@unban")) {
                        String me;
                        me = message.substring(spaceInd + 1, message.length());
                        unban(me);
                    }

                    if(command.equals("@exit")) {
                        sendMessage("User " + userName + " left chat.");
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



    private void ban(String name){
        if(isAdmin){
            synchronized (users) {
                for (ClientThread clientThread : users.getList()) {
                    if(clientThread.getUsername().equals(name)) {
                        clientThread.setBan(true);
                        sendMessage(clientThread.getUsername() + " is banned", userName);
                        sendMessage("Admin ban you", clientThread.getUsername());
                        return;
                    }
                }
                sendMessage("I cant find him", userName);
            }
        }
        else{
            sendMessage("you're not an admin, you can not", userName);
        }
    }

    private void unban(String name){
        if(isAdmin){
            synchronized (users) {
                for (ClientThread clientThread : users.getList()) {
                    if(clientThread.getUsername().equals(name)) {
                        clientThread.setBan(false);
                        sendMessage(clientThread.getUsername() + " is unbanned", userName);
                        sendMessage("Admin unban you", clientThread.getUsername());
                        return;
                    }
                }
                sendMessage("I cant find him", userName);
            }
        }
        else{
            sendMessage("you're not an admin, you can not", userName);
        }
    }

    private void sendMessage(String message) {
        if (isBanned) {
            sendMessage("Admin banned you. You cant send messege", userName);
            return;
        }

        PrintWriter out;
        synchronized (users) {
            for (ClientThread clientThread : users.getList()) {
                if (clientThread == this) {
                    continue;
                }
                out = clientThread.getWriter();
                out.println(message);
            }
        }
    }

    private void sendMessage(String message, String name) {
        if (isBanned) {
            //sendMessage("Admin banned you. You cant send messege", userName);
            return;
        }

        PrintWriter out;
        synchronized (users) {
            for (ClientThread clientThread : users.getList()) {
                if (clientThread.getUsername().equals(name)) {
                    out = clientThread.getWriter();
                    out.println(message);
                    break;
                }
            }
        }
    }

    public void setBan(boolean me){
        isBanned = me;
    }

    public boolean getBan(){
        return isBanned;
    }

    private void setAdmin() {
        if (isAdmin){
            sendMessage("You are current admin", userName);
            return;
        }
        else {
            synchronized (users) {
                for (ClientThread clientThread : users.getList()) {
                    if (clientThread.getAdmin()) {
                        sendMessage(userName + " trying become an admin", clientThread.getUsername());
                        sendMessage(clientThread.getUsername() + " is current admin", userName);
                        return;
                    }
                }
                sendMessage("Now you are admin", userName);
                isAdmin = true;
            }
        }
    }

    public boolean getAdmin(){
        return isAdmin;
    }

    public PrintWriter getWriter() {
        return out;
    }

    public BufferedReader getReader() {
        return in;
    }


    public String getUsername() {
        return userName;
    }

}
