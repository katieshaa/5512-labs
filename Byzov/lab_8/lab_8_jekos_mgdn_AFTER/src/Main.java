// Задание (Размещения):
//     Реализуйте многопоточную генерацию комбинаций А_без_повторений(n, k).
//     Подберите n,k и число потоков, чтобы выигрть у однопоточной версии.
//     A(n, k) = n! / (n - k)!

import org.suai.thread.PlacementThread;
import org.suai.parallel.ParallelPlacement;


public class Main {

    public static void main(String[] args) {
        try {
            int amountThreads = Runtime.getRuntime().availableProcessors();
            int n = 500;
            int k = 3;

            System.out.println("n = " + n + ", k = " + k);
            System.out.println();

            ParallelPlacement usual = new ParallelPlacement(1, n, k);
            long start = System.currentTimeMillis();
            long result = usual.makeSolution();
            long end = System.currentTimeMillis();
            System.out.println("Usual time = " + (end - start));
            System.out.println("Result = " + result);
            System.out.println();

            ParallelPlacement parallel = new ParallelPlacement(amountThreads, n, k);
            start = System.currentTimeMillis();
            result = parallel.makeSolution();
            end = System.currentTimeMillis();
            System.out.println("Parallel time = " + (end - start));
            System.out.println("Result = " + result);
        }
        catch(InterruptedException exception) {
            System.out.println(exception);
        }
    }
}