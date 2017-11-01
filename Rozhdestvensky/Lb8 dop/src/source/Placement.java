package source;


public class Placement {
    private int numThread;
    private int n;
    private int k;

    public Placement(int n_, int k_, int threads)
    {
        n = n_;
        k = k_;
        numThread = threads;
    }

    public long Solve() throws InterruptedException {
        if ((numThread <= 0) || (numThread > k)) {
            throw new RuntimeException("Incorrect count of Threads!");
        }
        long solve = 1;
        int start = 0;

        int elemCount = k / numThread;
        PlacmentThread[] threads = new PlacmentThread[numThread];

        for (int i = 0; i < threads.length; i++) {

            if (i == threads.length - 1) {
                elemCount = k - start;
            }

            threads[i] = new PlacmentThread(solve, n, start,start + elemCount);
            threads[i].start();
            start = start + elemCount;
        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
            solve *= threads[i].getSolve();
        }

        return solve;
    }
}
