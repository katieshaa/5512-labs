
/*Реализовать класс ParallelMatrixProduct для многопоточного умножения матриц UsualMatrix.
Ккласс получает число потоков для перемножения 
(число потоков может быть меньше, чем число строк у первой матрицы).
Сравнить время перемножения больших случайных матриц обычным и многопоточным способом. 
Получить текущее время можно с помощью методов класса System.
умножить кусок 21
*/
public class Main {

    public static void main(String[] args) throws InterruptedException {
    	ParallelMatrixProduct matrix = new ParallelMatrixProduct(3, 3, true);
        ParallelMatrixProduct matrix2 = new ParallelMatrixProduct(3, 3, true);

        long l1 = System.currentTimeMillis();
        UsualMatrix matrix3 = (UsualMatrix) matrix.mul(matrix2);
        long l2 = System.currentTimeMillis();
        
        System.out.println("UsualMatrix: " + (l2 - l1) + " ms");
        
        l1 = System.currentTimeMillis();
        UsualMatrix matrix4 = matrix.mulWithThreads(matrix2,2);
        l2 = System.currentTimeMillis();
        
        System.out.println("ParallelMatrixProduct: " + (l2 - l1) + " ms");

        System.out.println(matrix3.equals(matrix4));
    }
}
