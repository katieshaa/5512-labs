package org.suai.tasks;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;
import org.suai.prime.PrimeThread;


public class TaskQueue {

	private LinkedList<MyTask> queue = new LinkedList<>(); // очередь
	private int amountThreads;
	
	
	public TaskQueue(int amountThreads) {
		this.amountThreads = amountThreads;
	}
	
	
	public MyTask pop() {
		return this.queue.pop(); // выгружает первый элемент из очереди
	}


	public void push(MyTask task) {
		this.queue.add(task);
	}


	public void push(int start, int end) {
		push(new MyTask(start, end));
	}
	
	
	public void findPrimes() throws InterruptedException {
		int size = this.queue.size();

		if(size == 0) {
			return;
		}

		if(this.amountThreads > size || this.amountThreads <= 0) { 
			this.amountThreads = size;
		}
		
		PrimeThread[] threads = new PrimeThread[this.amountThreads];
		
		int i = 0;
		for(; size > 0; size--) {
			if(threads[i] != null) {
				threads[i].join();
				push(threads[i].getTask());//собираем задание
			}
			
			threads[i] = new PrimeThread(pop());//даем новое задание
			threads[i].start();
			
			i = (i + 1) % threads.length;
		}
		
		int j = i;
		while(true) {
			threads[j].join();
			push(threads[j].getTask());
			
			j = (j + 1) % threads.length;//идем по ккругу
			
			if(j == i) break;
		}
	}
	
	
	public void showResult() {
		StringBuilder builder = new StringBuilder();

		Iterator<MyTask> iterator = this.queue.iterator();
		ArrayList<Integer> answers = null;
		
		builder.append('[');
		
		while(iterator.hasNext()) {
			MyTask task = iterator.next();
			answers = task.getAnswers();

			builder.append(answers.toString());
		}
		
		builder.append(']');

		System.out.println(builder);
	}

}