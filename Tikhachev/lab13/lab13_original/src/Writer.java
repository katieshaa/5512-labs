import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 05.12.17 with love.
 */
public class Writer extends Thread {
    BufferedWriter out;
    private final String path = "/root/IdeaProjects/lab13/lab13_original/src/users";
    HashMap<String, HashMap<String, String>> mapForWrite;

    public Writer(HashMap<String, HashMap<String, String>> map) {
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }
            mapForWrite = map;
            out = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            for (Map.Entry<String, HashMap<String, String>> entry : mapForWrite.entrySet()) {
                out.write("~#~#~#" + entry.getKey() + "~#~#~#");
                out.newLine();
                for (Map.Entry<String, String> records : entry.getValue().entrySet()) {
                    out.write(records.getKey() + " " + records.getValue());
                    out.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
