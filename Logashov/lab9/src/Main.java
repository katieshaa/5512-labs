import java.util.Random;

/**
 * Created by Денис on 13.09.2017.
 */
public class Main {
    static Random r = new Random();
    static MyRandomList mr = new MyRandomList();
    //static MyRandomListCrash mr = new MyRandomListCrash();
    public static void main(String argc[])
    {
        MyWriteThread t1 = new MyWriteThread();
        MyReadThread t2 = new MyReadThread();
        t1.start();
        t2.start();
    }
    private static class MyWriteThread extends Thread
    {
        public void run()
        {
            while(true) {
                if (r.nextInt() % 2 == 1) {
                    mr.addNumber();
                } else {
                    mr.removeNumber();

                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    //
                }
            }
        }
    }
    private static class MyReadThread extends Thread
    {
        public void run()
        {
            while(true) {
                System.out.println(mr.calcZero());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    //
                }
            }
        }
    }
}
