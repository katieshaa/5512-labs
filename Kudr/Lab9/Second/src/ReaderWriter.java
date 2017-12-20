
import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class ReaderWriter {

    private HashMap<String, Integer> map = new HashMap<>();
    private FileThread[] fileThreads;
    private String[] fileNames;
    public ReaderWriter(String[] filenames) {
        fileNames = filenames;
        fileThreads = new FileThread[filenames.length];
    }

    public void scan() throws InterruptedException {
        for (int i = 0; i < fileNames.length; i++) {
            fileThreads[i] = new FileThread(new File(fileNames[i]));
            fileThreads[i].start();
        }

        for (int i = 0; i < fileThreads.length; i++) {
            fileThreads[i].join();
        }

        for (int i = 0; i < fileThreads.length; i++) {
            HashMap<String, Integer> temp = fileThreads[i].getMap();

            for (Map.Entry entry : temp.entrySet()) {
                if (map.containsKey(entry.getKey())) {
                    map.put((String) entry.getKey(), (int)entry.getValue() + map.get(entry.getKey()));
                } else {
                    map.put((String)entry.getKey(), (Integer) entry.getValue());
                }
            }
        }

    }



    public void printMap() {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }



}
