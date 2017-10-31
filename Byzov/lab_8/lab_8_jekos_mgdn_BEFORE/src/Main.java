/*
	| Thread |
	
	Реализовать класс ParallelMatrixProduct для многопоточного умножения матриц UsualMatrix.
	В конструкторе класс получает число потоков, которые будут использованы для перемножения
	(число потоков может быть меньше, чем число строк у первой матрицы).

	В функции main сравнить время перемножения больших случайных матриц обычным и многопоточным способом.
	Получить текущее время можно с помощью методов класса System.
*/
	
import java.util.Random;

import org.suai.matrix.UsualMatrix;
import org.suai.matrix.CellsProduct;
import org.suai.matrix.ParallelMatrixProduct;


public class Main {

	public static void main(String[] args) {
		try {
			int countAvailableProcessors = Runtime.getRuntime().availableProcessors();
			
			ParallelMatrixProduct firstMatrix = new ParallelMatrixProduct(100, 100, 1);
			ParallelMatrixProduct secondMatrix = new ParallelMatrixProduct(100, 100, 1);
			
			getRandMatrix(firstMatrix, 10);
			getRandMatrix(secondMatrix, 10);
			
			for(int i  = 0; i < 10; i++) {
				firstMatrix.setCountThreads(i + 1);
				
				long start = System.nanoTime();
				firstMatrix.productParallel(secondMatrix);
				long finish = System.nanoTime();
				
				System.out.println("Threads: " + (i + 1));
				System.out.println("Time: " + (finish - start));
				System.out.println();
			}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	

	public static void getRandMatrix(UsualMatrix matrix, int module) {
		Random rand = new Random();
		
		for(int i = 0; i < matrix.getRows(); i++) {
			for(int j = 0; j < matrix.getColumns(); j++) {
				matrix.setElement(i, j, rand.nextInt() % module);
			}
		}
	}
	
}