
import java.util.Vector;

public class ParallelVectorProduct extends Vector {
	int countThreads;
	
	public ParallelVectorProduct(int size,  int CountThreads) {
		super(size);
		countThreads = CountThreads;
	}
	
	public int getCountThreads() {
		return countThreads;
	}
	
	public void setCountThreads(int CountThreads) {
		countThreads = CountThreads;
	}
	
	public Vector productParallel(UsualMatrix Matrix) throws InterruptedException {
		if((countThreads <= 0) || (countThreads > Matrix.getColumns())) {
			throw new RuntimeException("Incorrect count of threads!");
		}
		
		if(this.size() != Matrix.getRows()) {
			throw new RuntimeException("Incorrect matrix for product!");
		}
		
		Vector result = new Vector( Matrix.getColumns());
		for(int i=0; i<Matrix.getColumns();i++) {
		result.add(new Integer(0));
		}
		
		
			int beginIndex = 0;
			int countsElements = result.size() / countThreads;
			MyTread[] threads = new MyTread[countThreads];
		
			for(int i = 0; i < threads.length; i++) {
				if(i == threads.length - 1) {
					countsElements = result.size() - beginIndex;
				}
			
				threads[i] = new MyTread (this, Matrix, result, beginIndex, beginIndex + countsElements);
				threads[i].start();
				beginIndex = beginIndex + countsElements;
			}
		
			for(int i = 0; i < threads.length; i++) {
				threads[i].join();
			}
		
		//System.out.println(result);
		return result;
	}
}
