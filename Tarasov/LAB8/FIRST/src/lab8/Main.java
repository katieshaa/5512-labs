package lab8;
/*Реализовать класс ParallelMatrixProduct для многопоточного умножения матриц UsualMatrix.
В конструкторе класс получает число потоков, которые будут использованы для перемножения (число потоков может быть меньше, чем число строк у первой матрицы).

В функции main сравнить время перемножения больших случайных матриц обычным и многопоточным способом. Получить текущее время можно с помощью методов класса System.
*/
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




        //System.out.println(matrix.mulWithTreads(matrix2));

    }
}
