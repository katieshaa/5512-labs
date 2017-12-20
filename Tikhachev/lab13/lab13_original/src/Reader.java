
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 05.12.17 with love.
 */
public class Reader {
    private BufferedReader in;
    private final String path = "/root/IdeaProjects/lab13/lab13_original/src/users";

    public Reader() {
        try {
            in = new BufferedReader(new FileReader(new File(path)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File users not found!");
        }
    }

    public HashMap<String, HashMap<String, String>> readFromFile() throws IOException {
        HashMap<String, HashMap<String, String>> result = new HashMap<>();
        HashMap<String, String> numbers = new HashMap<>();
        String tmp = in.readLine();
        String name = null;
        String key = null;
        String value;
        while(tmp != null) {
            if(tmp.contains("~#~#~#")) {
                if(numbers.size() > 0 && name != null) {
                    result.put(name, numbers);
                }
                name = tmp.replaceAll("~#~#~#", "");
                numbers = new HashMap<>();
                tmp = in.readLine();
            } else {
                String[] mas = tmp.split(" ");
                key = mas[0];
                value = mas[1];
                for(int i = 2; i < mas.length; i++) {
                    value += ";" + mas[i];
                }
                numbers.put(key, value);
                tmp = in.readLine();
            }
        }
        if(numbers.size() > 0 && name != null) {
            result.put(name, numbers);
        }
        in.close();
        return result;
    }
}
