package org.suai.thread;
import java.util.*;

import java.lang.Runnable;
import org.suai.matrix.UsualMatrix;


public class ProductThread implements Runnable {
	private UsualMatrix first;
	private UsualMatrix second;
	private UsualMatrix result;
	private int start;
	private int end;
	

	public ProductThread(UsualMatrix aFirst, UsualMatrix aSecond, UsualMatrix aResult, int aStart, int aEnd) {
		first = aFirst;
		second = aSecond;
		result = aResult;
		start = aStart;
		end = aEnd;
	}


	public void run() {
		long start1 = System.currentTimeMillis();
		if((first.getColumns() != second.getRows())
		|| (first.getRows() != result.getRows())
		|| (second.getColumns() != result.getColumns())
		|| (start < 0 || start >= result.getRows())
		|| (end < 0 || end > result.getRows())) {
			throw new RuntimeException("Cant product in thread! Bad matrix!");
		}
		for(int i = start; i < end; i++) {
			for(int j = 0; j < result.getColumns(); j++) {
				result.setCell(i, j, 0);
				
				int value = 0;
				for(int k = 0; k < first.getColumns(); k++) {
					value += (first.getCell(i, k) * second.getCell(k, j));
				}
				
				result.setCell(i, j, value);

			}
		}
		//System.out.println(Thread.currentThread().getName());
		System.out.println((System.currentTimeMillis()-start1) + " ---- "+ Thread.currentThread().getName());
	}
}