import java.util.ArrayList;

/**
 * Created by Куддус on 27.11.2017.
 */
public class AdList {
    private ArrayList<Ad> base = new ArrayList<>();
    public AdList(){}
    public synchronized void add(String h, String d, String n, String t) {
        base.add(new Ad(h,d,n,t));
    }
    public synchronized Ad getAd(int index) {
        return base.get(index);
    }
    public int size(){
        return base.size();
    }
    public class Ad {
        public String header;
        public String name;
        public String description;
        public String time;

        public Ad(String h, String d, String n, String t) {
            header = h;
            description = d;
            name = n;
            time = t;

        }


        public String getHeader() {
            return header;
        }

        public String getDescription() {
            return description;
        }

        public String getName() {
            return name;
        }

        public String getTime() {
            return time;
        }
    }



    }
