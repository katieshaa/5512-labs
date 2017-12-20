import java.util.ArrayList;

public class adsList {
    private ArrayList<ad> list = new ArrayList<ad>();

    public adsList(){    }

    public int size(){return list.size();}

    public synchronized void addAd(String h, String d, String u, String t){
        ad a = new ad(h, d, u, t);
        list.add(a);
    }

    public synchronized ad getAd(int index){
        return list.get(index);
    }
}

class ad{
    String header;
    String description;
    String username;
    String time;

    public ad(String h, String d, String u, String t){
        this.description = d;
        this.header = h;
        this.time = t;
        this.username = u;
    }

    public String getDescription() {
        return description;
    }

    public String getHeader() {
        return header;
    }

    public String getTime() {
        return time;
    }

    public String getUsername() {
        return username;
    }
}
