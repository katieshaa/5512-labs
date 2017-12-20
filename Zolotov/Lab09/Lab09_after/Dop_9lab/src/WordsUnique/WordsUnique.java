package WordsUnique;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class WordsUnique {
    private HashMap<String, Integer> map = new HashMap<>();

    public WordsUnique(String[] fileNames) {
        FileThread[] threads = new FileThread[fileNames.length];
        for (int i = 0; i < fileNames.length ; i++) {
            threads[i] = new FileThread(this, fileNames[i]);
            threads[i].start();
        }

        for(int j = 0; j < fileNames.length ; j++){
            threads[j].waiting();
        }
    }

    public synchronized void add(String w){
        if(map.containsKey(w))
            map.put(w, map.get(w) + 1);
        else
            map.put(w, 1);
    }

    public void readFile(String path) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(path));
        String word = "", line = "";
        while((line = in.readLine()) != null){
            System.out.println(line);
            for(int i = 0; i < line.length();i++){
                while(line.charAt(i) != ' '){
                    word += line.charAt(i);
                    if(line.length() - 1 == i)
                        break;
                    else
                        i++;
                }
                add(word);
                word = "";
            }

        }
        in.close();
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < map.size(); i++){
            sb.append(map.keySet().toArray()[i] + " " + map.values().toArray()[i] + '\n');
        }
        return sb.toString();
    }
}
