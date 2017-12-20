import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jekos_mgdn on 10.12.17
 */
public class ReaderWriter {
    private final String s = "C:\\Users\\Jekos_Mgdn\\IdeaProjects\\jekos_16_lab_new\\src\\list.txt";
    

    public HashMap<String, ArrayList<String>> read() {
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        synchronized (s) {
            try {
                BufferedReader in = new BufferedReader(new FileReader(new File(s)));
                String tmp = in.readLine();
                while(tmp != null) {
                    String[] mas = tmp.split(">>>");
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
