package matrix;

import java.lang.Thread;

public class MyTread extends Thread {
	UsualMatrix firstMatrix;
	UsualMatrix secondMatrix;
	UsualMatrix result;
	int beginIndex;
	int endIndex;
	
	
	public MyTread(UsualMatrix FirstMatrix, UsualMatrix SecondMatrix, UsualMatrix Result,
						int BeginIndex, int EndIndex) {
							firstMatrix = FirstMatrix;
							secondMatrix = SecondMatrix;
							result = Result;
							beginIndex = BeginIndex;
							endIndex = EndIndex;
	}
	
	
	public void run() {
		if(firstMatrix.getColumns() != secondMatrix.getRows()) {
			throw new RuntimeException("In thread incorrect matrix!");
		}
		
		if(result.getRows() != firstMatrix.getRows() || result.getColumns() != secondMatrix.getColumns()) {
			throw new RuntimeException("In thread incorrect matrix");
		}
		
		for(int index = beginIndex; (index < endIndex) && (index < result.getRows() * result.getColumns()); index++) {
			int i = index / result.getColumns();
			int j = index % result.getColumns();
			result.setElement(i, j, 0);
			
			for(int k = 0; k < firstMatrix.getColumns(); k++) {
				result.setElement(i, j, result.getElement(i, j) + (firstMatrix.getElement(i, k) * secondMatrix.getElement(k, j)));
			}
		}
	}
}