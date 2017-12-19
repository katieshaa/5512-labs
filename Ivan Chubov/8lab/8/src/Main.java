import org.suai.thread.ProductThread;
import org.suai.matrix.ParallelMatrixProduct;
import org.suai.matrix.UsualMatrix;


public  class Main {
	public static void main(String[] args) {
		try {
			int one = 1000;
			int two = 500;
			UsualMatrix first = new UsualMatrix(one, two);
			first.fillRandom(10);

			UsualMatrix second = new UsualMatrix(two, one);
			second.fillRandom(10);
			long start = System.currentTimeMillis();
			first.product(second);
			long end = System.currentTimeMillis();
			System.out.println("Without thread: " + (end - start));

			ParallelMatrixProduct product = new ParallelMatrixProduct(16);
			start = System.currentTimeMillis();
			product.parallelProduct(first, second);
			end = System.currentTimeMillis();
			System.out.println("With thread: " + (end - start));
		}
		catch(InterruptedException exception) {
			System.out.println(exception);
		}
	}
}
/*
	1. Прямоугольные матрицы
	2. Фиксануть кол-во потоков при делении
	3. Сравнение по кол-ву потоков от 1 до Н с обычным пермножением
 */