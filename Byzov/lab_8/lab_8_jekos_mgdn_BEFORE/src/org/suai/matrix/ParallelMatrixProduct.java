package org.suai.matrix;


public class ParallelMatrixProduct extends UsualMatrix {

	int countThreads;
	
	
	public ParallelMatrixProduct(int rows, int columns, int countThreads) {
		super(rows, columns);
		this.countThreads = countThreads;
	}
	
	
	public int getCountThreads() {
		return this.countThreads;
	}
	
	
	public void setCountThreads(int countThreads) {
		this.countThreads = countThreads;
	}
	
	
	public UsualMatrix productParallel(UsualMatrix other) throws InterruptedException {
		if((this.countThreads <= 0) || (this.countThreads > getRows() * other.getColumns())) {
			throw new RuntimeException("Incorrect count of Threads!");
		}
		
		if(getColumns() != other.getRows()) {
			throw new RuntimeException("Incorrect matrix for product!");
		}
		
		UsualMatrix result = new UsualMatrix(getRows(), other.getColumns());
		
		int beginIndex = 0;
		int countsElements = (result.getRows() * result.getColumns()) / this.countThreads;
		CellsProduct[] threads = new CellsProduct[this.countThreads];
		
		for(int i = 0; i < threads.length; i++) {
			if(i == threads.length - 1) {
				countsElements = result.getRows() * result.getColumns() - beginIndex;
			}
			
			threads[i] = new CellsProduct(this, other, result, beginIndex, beginIndex + countsElements);
			threads[i].start();
			beginIndex = beginIndex + countsElements;
		}
		
		for(int i = 0; i < threads.length; i++) {
			threads[i].join();
		}
		
		return result;
	}
	
}