import static java.lang.Thread.sleep;

/**
 * Created by Денис on 11.09.2017.
 */
public class Deadlock {
    private  static Object ob1 = new Object();
    private  static Object ob2 = new Object();
    public static void main(String[] argc)
    {
        lockTest1 l1 = new lockTest1();
        lockTest2 l2 = new lockTest2();
        l1.start();
        l2.start();
    }
    private static class lockTest1 extends Thread {
        public void run() {
            synchronized (ob1) {
                System.out.println("ob1 is selected");

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    //
                }
                synchronized (ob2) {
                    System.out.println("ob1 & ob2 is selected");
                }
            }
        }
    }
    private static class lockTest2 extends Thread {
        public void run() {
            synchronized (ob2) {
                System.out.println("ob2 is selected");

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    //
                }
                synchronized (ob1) {
                    System.out.println("ob1 & ob2 is selected");
                }
            }
        }
    }

}

