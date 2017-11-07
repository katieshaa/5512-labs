import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class FileThread extends Thread {
    private File file;
    private HashMap<String, Integer> map = new HashMap<>();
    private ReaderWriter readerWriter;

    public FileThread(File file, ReaderWriter readerWriter) {
        this.file = file;
        this.readerWriter = readerWriter;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String word = scanner.next();
                if (map.containsKey(word)) {
                    map.put(word, map.get(word) + 1);
                } else {
                    map.put(word, 1);
                }
            }

            readerWriter.putMapInMap(map);
        } catch (FileNotFoundException e) {
            System.out.println("File: " + file.getName() + " not found!");
        }

    }
}