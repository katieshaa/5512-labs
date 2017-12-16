import static java.lang.Thread.sleep;

public class DeadLock extends Thread{
    private Integer first;
    private Integer second;

    public DeadLock(Integer firstNumber, Integer secondNumber) {
        first = firstNumber;
        second = secondNumber;
    }

   
    public void run() {
        try {
            synchronized (first) {
                System.out.println(getName() + " trying to take another thread..");
                synchronized (second) {
                    System.out.println(getName() + " successfully take another thread");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
