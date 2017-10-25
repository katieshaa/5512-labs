
public class ParallelMatrixProduct {
	
	//private MyThread[] threads;
	private int numberOfThreads;
	
	public ParallelMatrixProduct(int number) {
		
		numberOfThreads = number;
		//threads = new MyThread[numberOfThreads];
	}
	
	public UsualMatrix product(UsualMatrix um1, UsualMatrix um2){
		
		if(um1.getColumns() != um2.getRows())
			throw new RuntimeException("Wrong sides!");
		
		UsualMatrix result = new UsualMatrix(um1.getRows(), um2.getColumns());
		int sizeForOneThread = (um1.getRows() * um2.getColumns()) / numberOfThreads;
		int begin = 0;
		
		MyThread[] threads = new MyThread[numberOfThreads];
		
		for(int i = 0; i < numberOfThreads; i++){
			int end = begin + sizeForOneThread;
			
			if(i == numberOfThreads - 1)
				end = um1.getRows() * um2.getColumns();
			
			threads[i] = new MyThread(um1, um2, result, begin, end);
			threads[i].start();
			begin = end;
	
		}
		
		for(int i = 0; i < numberOfThreads; i++) {
			threads[i].waitProduct();
		}
		
		return result;
	}
	
	
	
	
}
