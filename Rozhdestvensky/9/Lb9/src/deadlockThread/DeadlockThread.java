package deadlockThread;

/**
 * Created by Vengr on 04.11.2017.
 */
public class DeadlockThread extends Thread{
    private Integer first;
    private Integer second;

    public DeadlockThread(Integer firstNumber, Integer secondNumber) {
        first = firstNumber;
        second = secondNumber;
    }

    @Override
    public void run() {
        try {
            synchronized (first) {
                System.out.println(getName() + " trying to take another thread..");
                synchronized (second) {
                    System.out.println("if you see it, then I have problems");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
