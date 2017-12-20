package kek.maxan98.core.thread;
import java.util.*;

import java.lang.Runnable;

import kek.maxan98.core.MyMatrix;



public class ProductThread implements Runnable {
	private MyMatrix first;
	private MyMatrix second;
	private MyMatrix result;
	private int start;
	private int end;
	

	public ProductThread(MyMatrix aFirst, MyMatrix aSecond, MyMatrix aResult, int aStart, int aEnd) {
		first = aFirst;
		second = aSecond;
		result = aResult;
		start = aStart;
		end = aEnd;
	}


	public void run() {
		long start1 = System.currentTimeMillis();
		if((first.getcols() != second.getrows())
		|| (first.getrows() != result.getrows())
		|| (second.getcols() != result.getcols())
		|| (start < 0 || start >= result.getrows())
		|| (end < 0 || end > result.getrows())) {
			throw new RuntimeException("Cant product in thread! Bad matrix!");
		}
		for(int i = start; i < end; i++) {
			for(int j = 0; j < result.getcols(); j++) {
				result.setData(i, j, 0);
				
				int value = 0;
				for(int k = 0; k < first.getcols(); k++) {
					value += (first.getData(i, k) * second.getData(k, j));
				}
				
				result.setData(i, j, value);

			}
		}
		//System.out.println(Thread.currentThread().getName());
		System.out.println((System.currentTimeMillis()-start1) + " ---- "+ Thread.currentThread().getName());
	}
}