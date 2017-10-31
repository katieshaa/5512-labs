
public class dlThread extends Thread{
	private Integer firstNumber;
	private Integer secondNumber;
	private int result;
	
	public dlThread(Integer firstNumber, Integer secondNumber) {
		this.firstNumber = firstNumber;
		this.secondNumber = secondNumber;
	}
	
	public void run(){
		try{
			synchronized(firstNumber){
				System.out.println(getName() + " working...");
				synchronized(secondNumber){
					result = firstNumber + secondNumber;	
				}
			}
			System.out.println("Over.");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		Integer firstNumber = 10;
		Integer secondNumber = 30;
		dlThread thread1 = new dlThread(firstNumber, secondNumber);
		dlThread thread2 = new dlThread(secondNumber, firstNumber);
		
		thread1.start();
		thread2.start();
	}

}
