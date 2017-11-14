import deadlockThread.DeadlockThread;

/**
 * Created by Vengr on 04.11.2017.
 */
public class Main {
    public static void main(String[] args){

        Integer one = 1;
        Integer two = 2;

        DeadlockThread thread1 = new DeadlockThread(one, two);
        DeadlockThread thread2 = new DeadlockThread(two, one);

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
