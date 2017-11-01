package matrix;

/**
 * Created by Vengr on 6.9.2017.
 */
public class ParallelMatrixProduct extends Matrix {
    int numThreads;


    public ParallelMatrixProduct(int row, int col, int thread){
        super(row, col);
        numThreads = thread;
    }

    public Matrix productParallel(Matrix matr) throws InterruptedException {
        if ((numThreads <= 0) || (numThreads > this.getLen() * matr.getHei())) {
            throw new RuntimeException("Incorrect count of Threads!");
        }

        if (this.getLen() != matr.getHei()) {
            throw new RuntimeException("Incorrect matrix for product!");
        }

        Matrix result = new Matrix(this.getLen(), matr.getHei());

        int start = 0;
        int elemCount = (result.getLen() * result.getHei()) / numThreads;
        CellsProduct[] threads = new CellsProduct[numThreads];

        for (int i = 0; i < threads.length; i++) {
            if (i == threads.length - 1) {
                elemCount = result.getLen() * result.getHei() - start;
            }

            threads[i] = new CellsProduct(this, matr, result, start, start + elemCount);
            threads[i].start();
            start = start + elemCount;

        }

        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }

        return result;
    }

    public void setThreads(int thread)
    {
        numThreads = thread;
    }

}
