import java.util.LinkedList;
import java.util.Random;

import static java.lang.StrictMath.abs;

/**
 * Created by Денис on 13.09.2017.
 */
    public class MyRandomListCrash extends LinkedList<Integer> {
        private Random r = new Random();
        void addNumber() {
                add(r.nextInt());
        }
        void removeNumber() {
                if (size() != 0) {
                    remove(abs(r.nextInt() % size()));
                }
        }
        int calcZero() {
                int count = 0;
                for (int a : this) {
                    if (a == 0) {
                        count++;
                    }
                }
                return count;
        }

    }
