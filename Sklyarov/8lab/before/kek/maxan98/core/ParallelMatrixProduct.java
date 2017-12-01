package kek.maxan98.core;

import kek.maxan98.core.thread.ProductThread;

import java.lang.Thread;



public class ParallelMatrixProduct {
	private int amountThread;


	public ParallelMatrixProduct(int aAmountThread) {
		amountThread = aAmountThread;
	}


	public MyMatrix parallelProduct(MyMatrix aFirst, MyMatrix aSecond) throws InterruptedException {
		if(aFirst.getcols() != aSecond.getrows()) {
			throw new RuntimeException("Cant parallel product. Bad matrix.");
		}

		if(amountThread > aFirst.getrows() || amountThread <= 0) {
			amountThread = aFirst.getrows();//большн строк не бахай
		}

		MyMatrix result = new MyMatrix(aFirst.getrows(), aSecond.getcols());

		int amountRows = aFirst.getrows() / amountThread; //!!!!
		Thread threads[] = new Thread[amountThread];
		int start = 0;
		int end = 0;
		for(int i = 0; i < amountThread; i++ ) {
			start = end;
			end += amountRows;

			if(i == amountThread - 1) {
				end = aFirst.getrows();
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