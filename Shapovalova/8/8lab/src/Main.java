
import matrix.UsualMatrix;
import matrix.MyTread;
import matrix.ParallelMatrixProduct;
import java.util.Random;


public class Main {
	public static void main(String[] args) {
		try {
			ParallelMatrixProduct firstMatrix = new ParallelMatrixProduct(100, 100, 1);
			ParallelMatrixProduct secondMatrix = new ParallelMatrixProduct(100, 100, 1);
			
			getRandMatrix(firstMatrix, 10);
			getRandMatrix(secondMatrix, 10);
			
			long time1 = System.nanoTime();
			firstMatrix.productParallel(secondMatrix);
			long time2 = System.nanoTime();
			
			firstMatrix.setCountThreads(10);
			long time3 = System.nanoTime();
			firstMatrix.productParallel(secondMatrix);
			long time4 = System.nanoTime();
			
			System.out.println("Time 1: " + (time2 - time1));
			System.out.println("Time 2: " + (time4 - time3));
		}
		
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void getRandMatrix(UsualMatrix Matrix, int Module) {
		Random rand = new Random();
		
		for(int i = 0; i < Matrix.getRows(); i++) {
			for(int j = 0; j < Matrix.getColumns(); j++) {
				Matrix.setElement(i, j, rand.nextInt() % Module);
			}
		}
	}
}