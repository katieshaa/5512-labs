package lab8.dop;

public class NQueenWithThreads {
    private static int count = 0;
    public static int calc(int n) throws InterruptedException {
        count = 0;
        QueenThread[] queenThreads = new QueenThread[n];
        for (int i = 0; i < n; i++) {
            queenThreads[i] = new QueenThread(n, i);
            queenThreads[i].start();
        }



        for (int i = 0; i < queenThreads.length; i++) {
            queenThreads[i].join();
        }

        return count;
    }

    public static synchronized void increment() {
        count++;
    }
}
