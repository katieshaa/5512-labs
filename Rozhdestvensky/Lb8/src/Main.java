import matrix.*;
/*доп 6 в телефоне*/
import java.util.Random;

/**
 * Created by Vengr on 6.9.2017.
 */
public class Main {
    public static void main(String[] args) {
        try {
            int countAvailableProcessors = Runtime.getRuntime().availableProcessors();

            ParallelMatrixProduct matr1 = new ParallelMatrixProduct(1000, 500, 1);
            RandomFiller(matr1, 10);

            ParallelMatrixProduct matr2;
            /*ParallelMatrixProduct matr2 = new ParallelMatrixProduct(500, 1000, 1);
            RandomFiller(matr2, 10);*/

            for(int i  = 1; i < 6; i++) {
                matr2 = new ParallelMatrixProduct(500, 1000, i);
                RandomFiller(matr2, 10);

                /*matr2.setThreads(i + 1);*/

                long timeLap1 = System.currentTimeMillis();
                matr2.productParallel(matr1);
                long timeLap2 = System.currentTimeMillis();

                System.out.println("Threads: " + i);
                System.out.println("Time: " + (timeLap2 - timeLap1));

                timeLap1 = System.currentTimeMillis();
                matr2.product(matr1);
                timeLap2 = System.currentTimeMillis();

                System.out.println("Usual product Time: " + (timeLap2 - timeLap1) + "\n");
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void RandomFiller(Matrix me, int mod) {
        Random rand = new Random();

        for(int i = 0; i < me.getLen(); i++) {
            for(int j = 0; j < me.getHei(); j++) {
                me.setElement(i, j, rand.nextInt() % mod);
            }
        }
    }

}
