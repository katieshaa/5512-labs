import java.util.LinkedList;
import java.util.Random;

import static java.lang.StrictMath.abs;

/**
 * Created by Денис on 13.09.2017.
 */
public class MyRandomList extends LinkedList<Integer> {
    private Random r = new Random();
    void addNumber() {
        synchronized (this) {
            add(r.nextInt() % 10);
            System.out.println(this);
        }
    }
    void removeNumber() {
        synchronized (this) {
            if (size() != 0) {
                remove(abs(r.nextInt() % size()));
                System.out.println(this);
            }
        }
    }
    int calcZero() {
        synchronized (this) {
            int count = 0;
            for (int a : this) {
                if (a != 0) {
                    count++;
                }
            }
            return count;
        }
    }

}
