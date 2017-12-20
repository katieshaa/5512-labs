package org.suai.matrix;

import java.lang.Thread;
import org.suai.thread.ProductThread;
import org.suai.matrix.UsualMatrix;


public class ParallelMatrixProduct {
	private int amountThread;


	public ParallelMatrixProduct(int aAmountThread) {
		amountThread = aAmountThread;
	}


	public UsualMatrix parallelProduct(UsualMatrix aFirst, UsualMatrix aSecond) throws InterruptedException {
		if(aFirst.getColumns() != aSecond.getRows()) {
			throw new RuntimeException("Cant parallel product. Bad matrix.");
		}

		if(amountThread > aFirst.getRows() || amountThread <= 0) {
			amountThread = aFirst.getRows();
		}

		UsualMatrix result = new UsualMatrix(aFirst.getRows(), aSecond.getColumns());

		int amountRows = aFirst.getRows() / amountThread; //!!!!
		Thread threads[] = new Thread[amountThread];
		int start = 0;
		int end = 0;
		for(int i = 0; i < amountThread; i++ ) {
			start = end;
			end += amountRows;

			if(i == amountThread - 1) {
				end = aFirst.getRows();
			}

			ProductThread productThread = new ProductThread(aFirst, aSecond, result, start, end);
			threads[i] = new Thread(productThread);
			threads[i].start();
			//System.out.println(amountThread);
		}

		for(int i = 0; i < amountThread; i++) {
			threads[i].join();
		}

		return result;
	}
}