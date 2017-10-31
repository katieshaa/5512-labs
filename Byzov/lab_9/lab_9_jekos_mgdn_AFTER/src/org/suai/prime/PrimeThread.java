package org.suai.prime;


import java.util.ArrayList;
import org.suai.tasks.MyTask;


public class PrimeThread extends Thread {

	public MyTask task;
	
	
	public PrimeThread(MyTask task) {
		this.task = task;
	}


	public MyTask getTask() {
		return this.task;
	}
	
	
	public boolean isPrime(int number) {
		if(number == 2) {
			return true;
		}
		
		if(number % 2 == 0 || number == 1) {
			return false;
		}
		
		for(int i = 3; i * i <= number; i += 2) { // прибавляем два потому что пропускаем все четные, т.к. проверили их в предыдущем условии
			if(number % i == 0) {
				return false;
			}
		}
		
		return true;
	}
	

	public void run() {
		ArrayList<Integer> answers = new ArrayList<>();
		
		int start = this.task.getStart();
		int end = this.task.getEnd();
		
		for(int i = start; i < end; i++) {
			if(isPrime(i) == true) {
				answers.add(i);
			}
		}
		
		this.task.setAnswers(answers);
	}

}