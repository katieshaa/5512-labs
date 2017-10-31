import java.io.FileWriter;
import java.util.Random;

/**
 * Created by root on 14.07.17 with love.
 */
public class Main {

    private static int firstHeight = 1300;
    private static int firstWidth = 400;
    private static int secondHeight = firstWidth;
    private static int secondWidth = 753;
    private static int volume = 1000;
    private static long min = (long) Float.POSITIVE_INFINITY;
    private static int minT = 0;
    private static long max = (long) Float.NEGATIVE_INFINITY;
    private static int maxT = 0;

    public static void main(String[] args) {
        Knapsack knapsack = new Knapsack(volume, 30, 40, 228);
        knapsack.generateBoxes();
        long timeKnapsack = 0;

        for(int i = 1; i < 33; i++) {
            System.out.println("**********" + i + " Thread**********");
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
            System.out.println("*****************************");
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















        /*
        Random random = new Random(System.currentTimeMillis());

        UsualMatrix firstUsual;
        UsualMatrix secondUsual;
//        UsualMatrix resSumUsual;
        UsualMatrix resProductUsual;
        int rand = 0;
        long usualTimeSum = 0;
        long usualTimeProduct = 0;
//////////////////////////UsualMatrix
//        resSumUsual = new UsualMatrix(0,0);
        resProductUsual = new UsualMatrix(0,0);
        firstUsual = new UsualMatrix(firstHeight,firstWidth);
        secondUsual = new UsualMatrix(secondHeight, secondWidth);
        //first
        for(int i = 0; i < firstHeight; i++){
            for(int j = 0; j < firstWidth; j++){
                firstUsual.setElement(i, j, random.nextInt(61));
            }
        }
        //second
        for(int i = 0; i < secondHeight; i++){
            for(int j = 0; j < secondWidth; j++){
                secondUsual.setElement(i, j, random.nextInt(61));
            }
        }
        //////////////////////////////////
        try {
            try {
                System.out.println("////////////////////////////////////Sum UsualMatrix////////////////////////////////////");
                System.out.println("resSumUsual");
                usualTimeSum = System.currentTimeMillis();
//                resSumUsual = firstUsual.sum(secondUsual);
                usualTimeSum = System.currentTimeMillis() - usualTimeSum;
                System.out.println("Time = " + usualTimeSum + " ms");
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            try {
                System.out.println("////////////////////////////////////Product UsualMatrix////////////////////////////////////");
                System.out.println("resProductUsual");
                usualTimeProduct = System.currentTimeMillis();
                resProductUsual = firstUsual.product(secondUsual);
                usualTimeProduct = System.currentTimeMillis() - usualTimeProduct;
                System.out.println("Time = " + usualTimeProduct + " ms");
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
        catch(Exception ex) {
            System.out.println(ex.toString());
            return;
        }
//////////////////////////ParallelMatrixProduct
        System.out.println("////////////////////////////////////Product ParallelMatrix////////////////////////////////////");
        UsualMatrix resParallelProduct = new UsualMatrix(0, 0);
        long min = (long) Float.POSITIVE_INFINITY;
        int minT = 0;
        long max = (long) Float.NEGATIVE_INFINITY;
        int maxT = 0;
        for(int i = 1; i < 33; i++) {
            ParallelMatrixProduct parallelProduct = new ParallelMatrixProduct(i, firstUsual, secondUsual);
            long parallelTimeProduct = 0;
            parallelTimeProduct = System.currentTimeMillis();
            resParallelProduct = parallelProduct.product();
            parallelTimeProduct = System.currentTimeMillis() - parallelTimeProduct;
            /////
            try {
                usualTimeProduct = System.currentTimeMillis();
                resProductUsual = firstUsual.product(secondUsual);
                usualTimeProduct = System.currentTimeMillis() - usualTimeProduct;

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error usualProduct");
            }
            /////
            System.out.print(i + " threads: ");
            System.out.print("time = " + parallelTimeProduct + " ms");
            System.out.println("    UsualProduct = " + usualTimeProduct + " ms");
            if(parallelTimeProduct > max) {
                max = parallelTimeProduct;
                maxT = i;
            }
            if(parallelTimeProduct < min) {
                min = parallelTimeProduct;
                minT = i;
            }
        }
        System.out.println("Max time = " + max + " with " + maxT + " thread");
        System.out.println("Min time = " + min + " with " + minT + " thread");
        System.out.println("resProductUsual = resParallelProduct == " + resParallelProduct.equals(resProductUsual));
        */

    }
}
