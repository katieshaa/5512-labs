package WordsUnique;

public class FileThread extends Thread{
    public String path;
    public WordsUnique wu;
    public FileThread(WordsUnique wu, String path) {
        this.path = path;
        this.wu = wu;
    }
    public void run(){
        try{
            wu.readFile(path);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    public void waiting(){
        try {
            join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}