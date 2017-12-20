public class QueenNum extends Thread{

    private int numQueen;
    private int numThreads;
    private int idThread;

    private QueenNum(int numQueen, int numThreads, int idThread) {
        boolean[][] positionQueen = new boolean[numQueen][numQueen];
        this.numQueen = numQueen;
        this.numThreads = numThreads;
        this.idThread = idThread;
    }

    public void run(){
        for (int i = idThread; i < numQueen; i += numThreads) {
            LookSolutions newSol = new LookSolutions(numQueen, i, idThread);
            System.out.println("Thread : " + idThread + " New solution, pos: " + i);
            newSol.pushQueen(1);
        }
    }

    public static void main(String[] args) {
        int numQueens = Integer.parseInt(args[0]);
        int numThreads = 1;
//        int numThreads = Runtime.getRuntime().availableProcessors();
        System.out.println("Num of threads: " + numThreads);

        if(numThreads > numQueens){
            numThreads = numQueens;
        }

        QueenNum[] threads = new QueenNum[numThreads];

        for(int i = 0; i < numThreads; i++){
            threads[i] = new QueenNum(numQueens, numThreads, i); //
        }

        long startTime = System.currentTimeMillis();

        for(int i = 0; i < numThreads; i++){
            threads[i].start();
        }

        for(int i = 0; i < numThreads; i++){
            try{
                threads[i].join();
            } catch(InterruptedException e){
                System.out.println("Error: InterruptedException");
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("That took "+((endTime - startTime)/1000)+" seconds.");

    }
}