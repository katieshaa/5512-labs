
import java.util.Random;
import java.util.Vector;


public class Main {
	public static void main(String[] args) {
		try {
			int vectorsize =1000;
			UsualMatrix Matrix = new UsualMatrix(1000, 100);
			ParallelVectorProduct  vect  = new ParallelVectorProduct(vectorsize, 1);
			
			getRandMatrix(Matrix, 10);
			getRandVector(vect,vectorsize, 10);
			
			//System.out.println(Matrix);
			//System.out.println(vect);
			
			
			long time1 = System.nanoTime();
			vect.productParallel(Matrix);
			long time2 = System.nanoTime();
			
			vect.setCountThreads(3);
			long time3 = System.nanoTime();
			vect.productParallel(Matrix);
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
	public static void getRandVector(Vector vect,int vectorsize, int Module) {
		Random rand = new Random();
		
		for(int i = 0; i < vectorsize; i++) {
			vect.add(new Integer( rand.nextInt() % Module));
		}
	}
}