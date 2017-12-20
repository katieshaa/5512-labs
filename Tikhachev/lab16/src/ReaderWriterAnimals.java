import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by root on 15.12.17 with love.
 */
public class ReaderWriterAnimals {
    private final String path = "/root/IdeaProjects/lab16/src/animals.txt";

    public ReaderWriterAnimals() {

    }

    public HashMap<String, ArrayList<String>> read() {
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        synchronized (path) {
            try {
                BufferedReader in = new BufferedReader(new FileReader(new File(path)));
                String tmp = in.readLine();
                while(tmp != null) {
                    String[] mas = tmp.split("%%%%");
                    ArrayList<String> listTmp = new ArrayList<>();
                    for(int i = 1; i < mas.length; i++) {
                        listTmp.add(mas[i]);
                    }
                    map.put(mas[0], listTmp);
                    tmp = in.readLine();
                }
                in.close();
                return map;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
