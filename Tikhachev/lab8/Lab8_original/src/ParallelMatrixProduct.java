import java.util.LinkedList;
import java.util.ListIterator;

/**
 * Created by root on 14.07.17 with love.
 */
public class ParallelMatrixProduct {

    private int numbersOfThread;
    private UsualMatrix firstUsual;
    private UsualMatrix secondUsual;
    private UsualMatrix resProductMatrix;
    private LinkedList<MatrixThread> threadList;

    public ParallelMatrixProduct(int numberOfThreads, UsualMatrix firstUsual, UsualMatrix secondUsual) {
        this.numbersOfThread = numberOfThreads;
        this.firstUsual = firstUsual;
        this.secondUsual = secondUsual;
        this.resProductMatrix = new UsualMatrix(firstUsual.rows(), secondUsual.columns());
        this.threadList = new LinkedList<>();
    }

    public UsualMatrix product() {
        for(int i = 0; i < numbersOfThread; i++) {
            MatrixThread thread = new MatrixThread(("thread #" + i), i);
            threadList.add(thread);
            thread.start();

//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                System.out.printf("%s прерван\n", thread.getName());
//            }

        }
        try {
            ListIterator it = threadList.listIterator();
            while(it.hasNext()) {
                Thread tmp = (Thread) it.next();
                tmp.join();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
//        boolean end = false;
//
//        while(!end) {
//            end = true;
//            ListIterator<MatrixThread> it = threadList.listIterator();
//            while(it.hasNext()) {
//                if(it.next().isAlive()) {
//                    end = false;
//                }
//            }
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        return resProductMatrix;
    }

    public class MatrixThread extends Thread {

        private int numberThread;

        public MatrixThread(String name, int numberThread) {
            super(name);
            this.numberThread = numberThread;
        }

        @Override
        public void run() {
            for (int i = numberThread; i < firstUsual.rows(); i += numbersOfThread) {
                for (int j = 0; j < firstUsual.columns(); j++) {
                    for(int k = 0; k < firstUsual.columns(); k++) {
                        resProductMatrix.setElement(i, j, (resProductMatrix.getElement(i, j) +
                                firstUsual.getElement(i, k) * secondUsual.getElement(k, j)));
                    }
                }
            }
        }
    }


}
