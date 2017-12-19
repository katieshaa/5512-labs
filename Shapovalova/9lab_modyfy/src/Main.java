public class Main {
	public static void main(String[] args) {
		try {
			TaskQueue primeQueue = new TaskQueue(3);
			
			primeQueue.push(new MyTask(1, 20));
			primeQueue.push(new MyTask(20, 40));
			primeQueue.push(new MyTask(40, 60));
			primeQueue.push(new MyTask(60, 80));
			
			primeQueue.findPrimes();
			primeQueue.showResult();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}