package lab8;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ParallelMatrixProduct matrix = new ParallelMatrixProduct(500, 500, true, 10);
        ParallelMatrixProduct matrix2 = new ParallelMatrixProduct(500, 500, true, 10);

        long l1 = System.currentTimeMillis();
        matrix.mul(matrix2);
        long l2 = System.currentTimeMillis();
        System.out.println("UsualMatrix: " + (l2 - l1));
        l1 = System.currentTimeMillis();
        matrix.mulWithTreads(matrix2);
        l2 = System.currentTimeMillis();
        System.out.println("ParallelMatrixProduct: " + (l2 - l1));

    }
}
