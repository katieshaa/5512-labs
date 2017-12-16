package face.eshkere;


import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class CafeQueue {
    private LinkedList<Chelik> tourists = new LinkedList<>();
    private int curr;
    private int PC;
    private Integer freePC;
    private Chelik now;

    public CafeQueue(int numberTourists, int numberPC){
       // Collections.addAll(tourists, Chelik.genMan(numberTourists));
        for (int i = 0; i < numberTourists;i++){
            tourists.add(Chelik.genMan());
        }
        curr = numberTourists;
        PC = numberPC;
        freePC = numberPC;


    }
    public void LetsFuckingWorkTodayMafakaGoodNameForThisMethod(){

        LinkedList<CafeThread> list = new LinkedList<>();

        for(int i = 0; i < curr; i++) {
            CafeThread thread = new CafeThread();
            list.add(thread);
            thread.start();
            System.out.println("Strted "+ thread.getName());

        }
        ListIterator it = list.listIterator();

        while(it.hasNext()) {

            CafeThread tmp = (CafeThread) it.next();

            try {
                tmp.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public  Chelik removeT(){
        synchronized (tourists){
       return tourists.poll();}
    }


    public class CafeThread extends Thread{
        private boolean start = true;
        int curc;
        @Override
        public void run() {
                if (start == true){
                    start = false;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
synchronized (freePC) {
    if (freePC < 1) {

        dowork();
    }


    now = removeT();

    freePC--;}
    curc = now.getNumber();
    System.out.println("Chel #" + now.getNumber() + " Online! " + now.getTime());
    System.out.println("Free PC's" + freePC);
    try {
        Thread.sleep(now.getTime() * 100);
        System.out.println("bb ya svalil pc is free!" + curc);

        freePC++;
        System.out.println("Free PC's" + freePC);
        System.out.println(tourists.size() + "!!!");

    } catch (InterruptedException e) {
        e.printStackTrace();
    }





        }

        private void dowork() {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           // System.out.println("...");
            if(freePC < 1){
                dowork();
            }
            //run();
        }
    }
}
