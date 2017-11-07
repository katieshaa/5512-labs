
public class ParallelMatrixProduct extends UsualMatrix {

    public ParallelMatrixProduct(int row, int column, boolean rand) {
        super(row, column, rand);
    }

    public UsualMatrix mulWithThreads(UsualMatrix two, int threadCount) throws InterruptedException {
    	if (threadCount < 0)
            throw new RuntimeException("Потоков не может быть меньше нуля.");
    	
    	if (threadCount > getRow() * two.getColumn()) {
            throw new RuntimeException("Слишком много потоков:(");
        }

        if (getColumn() != two.getRow())
            throw new RuntimeException("Количество столбцов первой матрицы не равно количеству строк второй матрицы");
        UsualMatrix rezultMatrix = new UsualMatrix(getRow(), two.getColumn());

        int firstIND = 0;
        int add = 0;
        if (rezultMatrix.getRow() * rezultMatrix.getColumn() % threadCount != 0 ) {
            add++;
        }
        int countOfElem = rezultMatrix.getRow() * rezultMatrix.getColumn() / threadCount;
        countOfElem += add;

        MulThread threads[] = new MulThread[threadCount];

        for (int i = 0; i < threadCount; i++) {
            if (i == threadCount - 1) {
                countOfElem = rezultMatrix.getRow() * rezultMatrix.getColumn() - firstIND;
            }

            threads[i] = new MulThread(this, two, rezultMatrix, firstIND, firstIND + countOfElem);
            threads[i].start();

            firstIND += countOfElem;
        }


        for (int i = 0; i < threadCount; i++) {
            threads[i].join();
        }

        return rezultMatrix;
    }

}
