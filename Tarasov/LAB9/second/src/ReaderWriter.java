
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
            fileThreads[i] = new FileThread(new File(fileNames[i]), this);
            fileThreads[i].start();
        }

        for (int i = 0; i < fileThreads.length; i++) {
            fileThreads[i].join();
        }
    }

    public synchronized void putMapInMap(HashMap<String, Integer> otherMap) {
        for (Map.Entry<String, Integer> entry : otherMap.entrySet()) {
            if (map.containsKey(entry.getKey())) {
                map.put(entry.getKey(), entry.getValue() + otherMap.get(entry.getKey()));
            } else {
                map.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public void printMap() {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }



}
