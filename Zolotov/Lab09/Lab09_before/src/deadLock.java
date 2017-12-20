public class deadLock extends Thread{
    private final Integer firstNumber;
    private final Integer secondNumber;

    private deadLock(Integer firstNumber, Integer secondNumber) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
    }

    public void run(){
        try{
            synchronized(firstNumber){
                System.out.println(getName() + " working...");
                synchronized(secondNumber){
                    int result = firstNumber + secondNumber;
                }
            }
            System.out.println("The end.");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Integer firstNumber = 10;
        Integer secondNumber = 15;
        deadLock thread1 = new deadLock(firstNumber, secondNumber);
        deadLock thread2 = new deadLock(secondNumber, firstNumber);

        thread1.start();
        thread2.start();
    }

}