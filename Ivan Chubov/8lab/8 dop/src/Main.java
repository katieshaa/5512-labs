import java.util.List;
import java.util.ArrayList;
import java.util.Random;

import org.suai.merge.MergeThreads;


public class Main {

    public static ArrayList<Integer> generateRandomList(int size, int modulus) {
        ArrayList<Integer> randomArray = new ArrayList<>();
        Random random = new Random();

        while(size != 0) {
            randomArray.add(random.nextInt(modulus));

            size--;
        } 

        return randomArray;
    }


    public static void testParallel(int size, int modulus, int amountThreads, boolean isPrint) {
        try {
            List<Integer> randomArray = generateRandomList(size, modulus);

            if(isPrint) {
                System.out.println(randomArray);
            }

            MergeThreads parallelMerge = new MergeThreads(randomArray, amountThreads);

            long begin = System.currentTimeMillis();

            parallelMerge.start();
            parallelMerge.join();

            long end = System.currentTimeMillis();

            List<Integer> result = parallelMerge.getResult();

            if(isPrint) {
                System.out.println(result);
            }

            System.out.println("Time: " + (end - begin));

        }
        catch(InterruptedException exception) {
            System.out.println(exception);
            System.out.println("Oooops error! Bad interrupt thread!");
        }
    }


    public static void main(String[] args) {
        System.out.println("FIRST TEST:");
        testParallel(1000, 100, 1, false);

        System.out.println("SECOND TEST:");
        testParallel(1000, 100, 4, false);
    }

}