import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by jekos_mgdn on 10.12.17
 */
public class ReaderWriterAnimals {
    private final String path = "C:\\Users\\Jekos_Mgdn\\IdeaProjects\\lab16_jekos_mgdn\\lab16\\out\\production\\lab16\\animals.txt";

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
