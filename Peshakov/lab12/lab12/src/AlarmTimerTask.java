import java.util.TimerTask;

public class AlarmTimerTask extends TimerTask {
    private String name;
    private final UserList users;
    public AlarmTimerTask(ClientThread client, UserList userList) {
        name = client.getUsername();
        users = userList;
    }

    public void run() {
        synchronized (users) {
            for(ClientThread clientThread : users.getList()) {
                if(clientThread.getUsername().equals(name)) {
                    clientThread.getWriter().println("Wake up!");
                    return;
                }
            }
        }
    }
}
