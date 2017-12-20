import java.io.*;
import java.util.HashMap;

/**
 * Created by root on 08.12.17 with love.
 */
public class Reader {

    private String path = "/root/IdeaProjects/lab15/lab15_original/src/users.txt";

    public Reader() {

    }

    public HashMap<String, String> read() {
        try {
            HashMap<String, String> resultMap = new HashMap<>();
            BufferedReader in = new BufferedReader(new FileReader(new File(path)));
            String tmp = in.readLine();
            while(tmp != null) {
                String[] usernameAndPassword = tmp.split(";;;;;;;;;;;;;;;;;;;;;");
                resultMap.put(usernameAndPassword[0], usernameAndPassword[1]);
                tmp = in.readLine();
            }
            in.close();
            return resultMap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
