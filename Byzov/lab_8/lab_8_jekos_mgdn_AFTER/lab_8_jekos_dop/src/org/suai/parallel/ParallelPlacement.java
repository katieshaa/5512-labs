package org.suai.parallel;


import org.suai.thread.PlacementThread;


public class ParallelPlacement {

    private int amountThread;
    private int n;
    private int k;


    public ParallelPlacement(int amountThread, int n, int k) {
        this.amountThread = amountThread;
        this.n = n;
        this.k = k;
    }


    public long makeSolution() throws InterruptedException {
        this.amountThread = 2;

        long result = 0;
        int start = 0;
        int end = 0;
        int step = this.n / this.amountThread;

        PlacementThread[] threads = new PlacementThread[this.amountThread];
        for(int i = 0; i < threads.length; i++) {
            start = end;
            end = ((i == threads.length - 1) ? this.n : end + step);

            threads[i] = new PlacementThread(this.n, this.k, start, end);
            threads[i].start();
        }

        for(int i = 0; i < threads.length; i++) {
            threads[i].join();
            result += threads[i].getCount();
        }

        return result;
    }

}