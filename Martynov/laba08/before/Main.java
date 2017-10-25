//import java.util.Arrays;
//import java.util.Random;

public class Main {
	
	public static void main(String[] args){
		
		try{
			int size = 900;
			
			/*
			 * Usual
			 */
			
			UsualMatrix um = new UsualMatrix(size, size);
			um.fillMatrixRand(4);
			
			UsualMatrix um2 = new UsualMatrix(size, size);
			um2.fillMatrixRand(4);
			
			long t1 = System.currentTimeMillis();
			um.product(um2);
			long t2 = System.currentTimeMillis();
			
			/*
			 * Parallel
			 */
			
			UsualMatrix umm = new UsualMatrix(size, size);
			umm.fillMatrixRand(4);
			
			UsualMatrix umm2 = new UsualMatrix(size, size);
			umm2.fillMatrixRand(4);
			
			ParallelMatrixProduct pmp = new ParallelMatrixProduct(2);
			
			long t3 = System.currentTimeMillis();
			pmp.product(umm, umm2);
			long t4 = System.currentTimeMillis();
			
			/*
			 * Result
			 */
			
			System.out.println("First test " + (t2 - t1));
			System.out.println("Second test " + (t4 - t3));
			
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
	}

}
