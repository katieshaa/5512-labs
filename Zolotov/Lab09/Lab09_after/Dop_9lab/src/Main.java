import WordsUnique.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        if (args.length > 0) {
        String[] fileNames = new String[args.length];
        for(int i = 0; i < args.length; i++) {
            fileNames[i] = args[i];
        }
        WordsUnique wu = new WordsUnique(fileNames);
        System.out.println(wu);
        } else {
            System.out.println("Error. Input filenames!");
            System.exit(0);
        }
    }
}