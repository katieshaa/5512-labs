package cafe;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by Vengr on 05.11.2017.
 */

public class Computer {

    private MyQueue queue;
    private int numC;

    public Computer(int numberTourists, int numberComputers){
        queue = new MyQueue(numberTourists);
        numC = numberComputers;
    }

    public void cafe(){

        LinkedList<ComputerThread> list = new LinkedList<>();

        for(int i = 0; i < numC; i++) {
            ComputerThread thread = new ComputerThread();
            list.add(thread);
            thread.start();
        }

        ListIterator it = list.listIterator();

        while(it.hasNext()) {

            Thread tmp = (Thread) it.next();

            try {
                tmp.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

    }




    private class ComputerThread extends Thread {

        @Override
        public void run() {
            try {
                Tourist tourist;
                while (!queue.isEmpty()) {

                    synchronized (queue) {
                        tourist = queue.remove();

                    }
                    System.out.println("Tourist " + (tourist.getNumber() + 1) + " is online");
                    /*
                    //про то что следующий парень ждёт своего выхода
                    if(!queue.isEmpty()){
                        System.out.println("Tourist " + (queue.currentTourist().getNumber() + 1) + " waiting for turn");
                    }
                    */

                    try {
                        Thread.sleep(tourist.getTime());
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }

                    System.out.println("Tourist " + (tourist.getNumber() + 1) + " is done, having spent " + tourist.getTime() / 100 + " minutes online");

                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
