package lab8;

public class ParallelMatrixProduct extends UsualMatrix {

    private int threadCount = 1;
    public ParallelMatrixProduct(int row, int column, boolean rand, int threadCount) {
        super(row, column, rand);
        if (threadCount < 0)
            throw new RuntimeException("Потоков не может быть меньше нуля!");

        this.threadCount = threadCount;
    }

    public UsualMatrix mulWithTreads(UsualMatrix two) throws InterruptedException {
        if (threadCount > getRow() * two.getColumn()) {
            throw new RuntimeException("Потоков не может быть больше произведения строк и столбцов двух матриц");
        }

        if (getColumn() != two.getRow())
            throw new RuntimeException("Умножение матриц невозможно!\nКоличество столбцов первой матрицы не равно количеству строк второй матрицы.");
        UsualMatrix rezultMatrix = new UsualMatrix(getRow(), two.getColumn(), false);

        int firstIND = 0;
        int countOfElem = rezultMatrix.getRow() * rezultMatrix.getColumn() / threadCount;

        ThreadsCell threadsCells[] = new ThreadsCell[threadCount];

        for (int i = 0; i < threadCount; i++) {
            if (i == threadCount - 1) {
                countOfElem = rezultMatrix.getRow() * rezultMatrix.getColumn() - firstIND;
            }

            threadsCells[i] = new ThreadsCell(this, two, rezultMatrix, firstIND, firstIND + countOfElem);
            threadsCells[i].start();

            firstIND += countOfElem;
        }


        for (int i = 0; i < threadCount; i++) {
            threadsCells[i].join();
        }

        return rezultMatrix;
    }

}
