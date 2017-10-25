import java.util.Random;
import java.util.Arrays;
import java.util.Formatter;


public class Qsort extends Thread{
	private Object[] arr;
	private int low;
	private int high;
	private static int threadsCount = 0;

	public Qsort(Object[] array, int lower, int higher, int threads) {
		arr = array;
		low = lower;
		high = higher;
		threadsCount = threads;
	}


	public Qsort(Object[] array, int lower, int higher) {
		arr = array;
		low = lower;
		high = higher;
	}



	public static void main (String[] agrs) {
		Random generator = new Random();
		int s = 100000;
		Integer[] array = new Integer[s];
		Integer[] arr = new Integer[s];

		long t1;

		for (int i = 0; i < array.length; i++) {
			array[i] = generator.nextInt(51);
			arr[i] = array[i];	
		}

		t1 = System.currentTimeMillis();
		quicksort(array, 0, array.length - 1);

		System.out.println("Without parallel " + (System.currentTimeMillis() - t1));

		try { 
			Qsort qSort = new Qsort(array, 0, arr.length - 1, 2);

			t1 = System.currentTimeMillis();
			qSort.run();
			
			System.out.println("With parallel " + (System.currentTimeMillis() - t1));//System.out.println(Arrays.toString(array));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void run(){
		try {
			parralelQuickSort(arr, low, high);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void parralelQuickSort(Object[] array, int lower, int higher) throws Exception{
		if (lower >= higher) {
			return;
		}

		int p = partition(array, lower, higher);
		
		if (threadsCount > 0) {
			Qsort quicksortObject1 = new Qsort(array, lower, p);
		
			quicksortObject1.start();
			threadsCount--;

			if (threadsCount > 0) {
				Qsort quicksortObject2 = new Qsort(array, p + 1, higher);
		
				quicksortObject2.start();
				threadsCount--;
				quicksortObject2.join();
			} else {
				quicksort(array, p + 1, higher);
			}

			quicksortObject1.join();
		} else {
			quicksort(array, lower, p);
		}

	}


	public static void quicksort(Object[] array, int lower, int higher) {
		long t1 = System.currentTimeMillis();
		if (lower < higher) {
			int p = partition(array, lower, higher);
			
			quicksort(array, lower, p);
			quicksort(array, p + 1, higher);
		}
		//System.out.println(System.currentTimeMillis() - t1);
	}


	private static int partition(Object[] array, int lower, int higher) {
		Object pivot = array[((lower + higher) / 2)];
		int i = lower;
		int j = higher;

		while (true) {
			while (( (Comparable) array[j]).compareTo(pivot) > 0) {
				j--;
			}

			while (( (Comparable) array[i]).compareTo(pivot) < 0 && i <= j) {
				i++;
			}

			if (i >= j) {

				return j;
			}

			Object tmp = array[j];
			array[j] = array[i];
			array[i] = tmp;


			i++;
			j--;
		}
	}
}