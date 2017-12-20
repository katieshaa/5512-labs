package matrix;


public class ParallelMatrixProduct extends UsualMatrix {
	int countThreads;
	
	public ParallelMatrixProduct(int Rows, int Columns, int CountThreads) {
		super(Rows, Columns);
		countThreads = CountThreads;
	}
	
	public int getCountThreads() {
		return countThreads;
	}
	
	public void setCountThreads(int CountThreads) {
		countThreads = CountThreads;
	}
	
	public UsualMatrix productParallel(UsualMatrix Matrix) throws InterruptedException {
		if((countThreads <= 0) || (countThreads > this.getRows() * Matrix.getColumns())) {
			throw new RuntimeException("Incorrect count of threads!");
		}
		
		if(this.getColumns() != Matrix.getRows()) {
			throw new RuntimeException("Incorrect matrix for product!");
		}
		
		UsualMatrix result = new UsualMatrix(this.getRows(), Matrix.getColumns());
		
		int beginIndex = 0;
		int countsElements = (result.getRows() * result.getColumns()) / countThreads;
		MyTread[] threads = new MyTread[countThreads];
		
		for(int i = 0; i < threads.length; i++) {
			if(i == threads.length - 1) {
				countsElements = result.getRows() * result.getColumns() - beginIndex;
			}
			
			threads[i] = new MyTread(this, Matrix, result, beginIndex, beginIndex + countsElements);
			threads[i].start();
			beginIndex = beginIndex + countsElements;
		}
		
		for(int i = 0; i < threads.length; i++) {
			threads[i].join();
		}
		
		return result;
	}
}