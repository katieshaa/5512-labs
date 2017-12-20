public class Main {
    public static void main(String[] args){

        Integer numberOne = 20;
        Integer numberTwo = 50;

        DeadLock thread1 = new DeadLock(numberOne, numberTwo);
        DeadLock thread2 = new DeadLock(numberTwo, numberOne);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}