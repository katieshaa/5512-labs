import java.awt.image.AreaAveragingScaleFilter;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 15.12.17 with love.
 */
public class ReaderWriterAdverts {
    private final String path = "/root/IdeaProjects/lab15/lab15_original/src/adverts.txt";

    public ReaderWriterAdverts() {

    }

    public ArrayList<HashMap<String, String>> read() {
        long milis = System.currentTimeMillis();
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        try {
            synchronized (path) {
                BufferedReader in = new BufferedReader(new FileReader(new File(path)));
                String tmp = in.readLine();
                String key;
                String value;
                while (tmp != null) {
                    key = tmp;
                    value = in.readLine();
                    tmp = in.readLine();
                    HashMap<String, String> map = new HashMap<>();
                    map.put(key, value);
                    list.add(map);
                }
                in.close();
            }
            milis = System.currentTimeMillis() - milis;
            System.out.println("Read " + milis);
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void write(ArrayList<HashMap<String, String>> list) {
        ThreadWriter thread = new ThreadWriter(list);
        thread.start();
    }

    private class ThreadWriter extends Thread {

        private ArrayList<HashMap<String, String>> list;

        public ThreadWriter(ArrayList<HashMap<String, String>> lst) {
            list = lst;
        }

        @Override
        public void run() {
            long milis = System.currentTimeMillis();
            synchronized (path) {
                File file = new File(path);
                if (file.exists()) {
                    file.delete();
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    BufferedWriter out = new BufferedWriter(new FileWriter(new File(path)));
                    for (int i = 0; i < list.size(); i++) {
                        for (Map.Entry<String, String> entry : list.get(i).entrySet()) {
                            out.write(entry.getKey());
                            out.newLine();
                            out.write(entry.getValue());
                            out.newLine();
                        }
                    }
                    out.flush();
                    out.close();
                    milis = System.currentTimeMillis() - milis;
                    System.out.println("Write " + milis);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
