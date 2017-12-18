import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Куддус on 03.12.2017.
 */
public class ListReader {
    private String file;

    public ListReader(String tmp) {
        file = tmp;
    }

    public synchronized ArrayList<ArrayList<String>> read() {
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String srting;

            while ((srting = reader.readLine()) != null) {
                int size = list.size();
                if (srting.startsWith("+")) {
                    ArrayList<String> tmp = new ArrayList<>();
                    tmp.add(srting.substring(1));
                    list.add(tmp);
                } else if (srting.startsWith("    +") && size != 0) {
                    list.get(size - 1).add(srting.substring(5));
                } else
                    System.out.println("error read");

            }
        }
        catch (Exception e){};
        return list;
    }

}
