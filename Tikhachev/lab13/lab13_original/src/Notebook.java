import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.io.IOException;
import java.util.*;


public class Notebook {
    private HashMap<String, String> numbers;
    private HashMap<String, HashMap<String, String>> users;

    public Notebook() {
        Reader reader = new Reader();
        try {
            users = reader.readFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean existName(String username) {
        if(users.get(username) != null) {
            return true;
        } else {
            return false;
        }
    }

    public HashMap<String, String> getRecords(String username) {
        return users.get(username);
    }

    public void addNumber(String username, String name, String number) throws NotFound {
        HashMap<String, String> tmp = users.get(username);
        if (tmp != null) {
            String tmpString = (String) tmp.get(name);
            if(tmpString != null) {
                tmpString += " " + number;
                tmp.put(name, tmpString);
            } else {
                tmp.put(name, number);
            }
            Writer thread = new Writer(users);
            thread.start();
        } else {
            throw new NotFound();
        }
    }

    public void addUser(String username) {
        users.computeIfAbsent(username, k -> new HashMap<String, String>());
        Writer thread = new Writer(users);
        thread.start();
    }

    public Map<String, String> getSortedMap(String username) {
        if(existName(username)) {
            Map<String, String> treeMap = new TreeMap<String, String>(users.get(username));
            return treeMap;
        } else {
            return null;
        }
    }

}
