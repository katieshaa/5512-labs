import java.lang.Thread;
import java.util.ArrayList;

public class PrimeThread extends Thread {
	public MyTask task;
	
	public PrimeThread(MyTask Task) {
		task = Task;
	}
	
	public boolean isPrime(int Number) {
		if(Number == 2) {
			return true;
		}
		
		if(Number % 2 == 0 || Number == 1) {
			return false;
		}
		
		for(int i = 3; i * i <= Number; i += 2) {
			if(Number % i == 0) {
				return false;
			}
		}
		
		return true;
	}
	
	public MyTask getTask() {
		return task;
	}
	
	public void run() {
		ArrayList<Integer> answers = new ArrayList<> ();
		
		int start = task.getStart();
		int end = task.getEnd();
		
		for(int i = start; i < end; i++) {
			if(isPrime(i)) {
				answers.add(i);
			}
		}
		
		task.setAnswers(answers);
	}
}