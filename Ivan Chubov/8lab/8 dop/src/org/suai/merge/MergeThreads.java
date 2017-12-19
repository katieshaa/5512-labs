package org.suai.merge;


import java.util.List;
import java.util.ArrayList;


public class MergeThreads extends Thread {

	private List<Integer> array;
	private List<Integer> sortedArray = null;

	private int amountThreads;


	public MergeThreads(List<Integer> array, int amountThreads) {
		this.array = array;
		this.amountThreads = amountThreads;
	}


	public List<Integer> getResult() {
		return this.sortedArray;
	}


	@Override
	public void run() {
		try {
			this.sortedArray = beginParallel(this.array, this.amountThreads);
		}
		catch(InterruptedException exception) {
			System.out.println(exception);
			System.out.println("Oooops error! Bad interrupt thread!");
		}
	}


	public List<Integer> beginParallel(List<Integer> array, int amountThreads) throws InterruptedException {
		if(amountThreads <= 1) {
			return beginUsual(array);
		}

		int size = array.size();
		int middle = size / 2;

		MergeThreads leftSide = new MergeThreads(array.subList(0, middle), amountThreads / 2);
		MergeThreads rightSide = new MergeThreads(array.subList(middle, size), amountThreads / 2);

		leftSide.start();
		rightSide.start();

		leftSide.join();
		rightSide.join();

		return merge(leftSide.getResult(), rightSide.getResult());
	}


	public List<Integer> beginUsual(List<Integer> array) {
		if(array.size() <= 1) {
			return array;
		}

		int size = array.size();
		int middle = size / 2;

		List<Integer> leftSide = beginUsual(array.subList(0, middle));
		List<Integer> rightSide = beginUsual(array.subList(middle, size));

		return merge(leftSide, rightSide);
	}


	public List<Integer> merge(List<Integer> firstArray, List<Integer> secondArray) {
		int firstSize = firstArray.size();
		int secondSize = secondArray.size();

		int i = 0;
		int j = 0;

		List<Integer> mergeArray = new ArrayList<>(firstSize + secondSize);


		while(i < firstSize && j < secondSize) {
			if (firstArray.get(i) < secondArray.get(j)) {
				mergeArray.add(firstArray.get(i++));
			}
			else {
				mergeArray.add(secondArray.get(j++));
			}
		}
		while(i < firstSize) {
			mergeArray.add(firstArray.get(i++));
		}
		while(j < secondSize) {
			mergeArray.add(secondArray.get(j++));
		}

		return mergeArray;
	}

}
// на object!!!!!