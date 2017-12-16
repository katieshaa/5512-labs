import java.io.FileWriter;
import java.util.Random;


public class Main {


    private static int volume = 1000;
    private static long min = (long) Float.POSITIVE_INFINITY;
    private static int minT = 0;
    private static long max = (long) Float.NEGATIVE_INFINITY;
    private static int maxT = 0;

    public static void main(String[] args) {
        Knapsack knapsack = new Knapsack(volume, 30, 40, 240);
        knapsack.generateBoxes();
        long timeKnapsack = 0;

        for(int i = 1; i < 34; i++) {
            System.out.println("THREAD " + i + " THREAD");
            try {
                timeKnapsack = System.currentTimeMillis();
                byte[] answer = knapsack.solveKnapsack(i);
                timeKnapsack = System.currentTimeMillis() - timeKnapsack;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.toString());
            }
            knapsack.printBoxes();
            System.out.println("Volume = " + volume);
            System.out.println("Cost = " + knapsack.getAnswerCost());
            System.out.println("Time = " + timeKnapsack + " ms");
            System.out.println("______________________________");
            if(timeKnapsack > max) {
                max = timeKnapsack;
                maxT = i;
            }
            if(timeKnapsack < min) {
                min = timeKnapsack;
                minT = i;
            }
        }

        System.out.println("Max time = " + max + " with " + maxT + " thread");
        System.out.println("Min time = " + min + " with " + minT + " thread");


    }
}