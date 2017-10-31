import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by root on 24.10.17 with love.
 */
public class Computer {
    private int numberOfComputer;
    private int numberOfTourist;
    private Queue queue;

    public Computer(int numberC, int numberT) {
        numberOfComputer = numberC;
        numberOfTourist = numberT;
        queue = new Queue(numberOfTourist);
        queue.printTiming();
    }

    public void startCafe() throws InterruptedException {
        LinkedList<UseComputer> list = new LinkedList<>();
        for(int i = 0; i < numberOfComputer; i++) {
            UseComputer thread = new UseComputer("Computer #" + i);
            list.add(thread);
            thread.start();
        }
        ListIterator it = list.listIterator();
        while(it.hasNext()) {
            Thread tmp = (Thread) it.next();
            tmp.join();
        }
    }

    private class UseComputer extends Thread {

        public UseComputer(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                Tourist tourist;
                while (!queue.isEmpty()) {
                    synchronized (queue) {
                        tourist = queue.remove();
                     //   queue.waitingForTurn();
                    }
                    System.out.println("Tourist " + tourist.getNumber() + " is online");
                    try {
                        Thread.sleep(tourist.getTimeUseComputer());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Tourist " + tourist.getNumber() + " is done");
                }
            } catch (Exception e) {

            }
            System.out.println(Thread.currentThread().getName() + " is empty");
        }
    }
}
