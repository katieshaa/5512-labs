/*6. Реализуйте многопоточную генерацию комбинаций А с повторениями (n,k).
Подберите n, k и число потоков, чтобы выиграть у однопоточной версии
А - это размещение
А с повторениями (n,k) - это n^k
 */


import source.Placement;

public class Main {
    public static void main(String[] args) {
        try {

            int n = 10;
            int k = 7;

            System.out.println("n = " + n + ", k = " + k);
            System.out.println();

            Placement usual = new Placement(n, k, 1);
            long start = System.nanoTime();
            long result = usual.Solve();
            long end = System.nanoTime();

            System.out.println("1 thread time = " + (end - start));
            System.out.println("Result = " + result + "\n");

            for(int i = 2; i < k; i++) {
                Placement parallel = new Placement(n, k, i);

                start = System.nanoTime();
                result = parallel.Solve();
                end = System.nanoTime();

                System.out.println("Parallel " + i + " threads time = " + (end - start));
                System.out.println("Result = " + result);
            }
        }
        catch(InterruptedException exception) {
            System.out.println(exception);
        }
    }
}
