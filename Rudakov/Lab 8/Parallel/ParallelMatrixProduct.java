package Parallel;

import matrix.UsualMatrix;

public class ParallelMatrixProduct{
    private ProductThread[] threads;
    private UsualMatrix first;
    private UsualMatrix second;
    private UsualMatrix result;
    private class ProductThread extends Thread {
        private int n;
        ProductThread(int n) {
            this.n = n;
        }
        public void run() {
            for(int i = 0; i < second.getColumns(); ++i) {
                int tmp = 0;
                for(int j = 0; j < first.getColumns(); ++j) {
                    tmp += first.getElement(n,j) * second.getElement(j, i);
                }
                result.setElement(n, i, tmp);
            }
        }
    }

    public ParallelMatrixProduct (int N) {
        threads = new ProductThread[N];
    }
    public UsualMatrix product(UsualMatrix a, UsualMatrix b) {
        this.first = a;
        this.second = b;
        this.result = new UsualMatrix(first.getRows(), second.getColumns());
        if(first.getColumns() != second.getRows()) {
            throw new RuntimeException("This matrixes cann't be multiplied");
        }
        for(int i = 0, j = 0; i < first.getRows(); ++j, j %= threads.length) {
            if(threads[j] != null) {
                if(!threads[j].isAlive()) {
                    threads[j] = new ProductThread(i);
                    threads[j].start();
                    ++i;
                }
                else {
                    continue;
                }
            }
            else {
                threads[j] = new ProductThread(i);
                threads[j].start();
                ++i;
            }
        }
        for(int i = 0; i < threads.length; ++i) {
            if(threads[i] != null) {
                try {
                    threads[i].join();
                }
                catch (InterruptedException iEx) {
                    System.out.print("Bad join with thread:");
                    System.out.println(i);
                }
            }
        }
        return result;
    }
}
