import java.util.LinkedList;
import java.lang.StringBuilder;
import java.util.ListIterator;

public class TaskQueue {
	public LinkedList<MyTask> queue;
	public int countThreads;
	
	
	public TaskQueue(int threads) {
		queue = new LinkedList<> ();
		countThreads = threads;
	}
	
	
	public MyTask pop() {
		return queue.pop();
	}
	
	
	public void push(MyTask newTask) {
		queue.add(newTask);
	}
	
	
	public void setCountThreads(int CountThreads) {
		countThreads = CountThreads;
	}
	
	
	public void findPrimes() throws InterruptedException{
		
		if(queue.size() == 0) return;
		
		int size = queue.size();
		
		if (countThreads > size) 
			setCountThreads(size); 
		PrimeThread[] threads = new PrimeThread[countThreads];
		
		int i = 0;
		for(; size > 0; size--) {
			if(threads[i] != null) {
				threads[i].join();
				push(threads[i].getTask());
			}
			
			threads[i] = new PrimeThread(pop());
			threads[i].start();
			
			i = (i + 1) % threads.length;
		}
		
		int j = i;
		while(true) {
			threads[j].join();
			push(threads[j].getTask());
			
			j = (j + 1) % threads.length;
			
			if(j == i) break;
		}
	}
	
	
	public void showResult() {
		ListIterator<MyTask> iterator = queue.listIterator();
		
		
		while(iterator.hasNext()) {
			MyTask task = iterator.next();
			
			System.out.print(task.getAnswers());
		}
	}
}
