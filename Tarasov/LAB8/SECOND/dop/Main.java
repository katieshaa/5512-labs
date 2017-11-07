package lab8.dop;

import java.lang.InterruptedException;

public class Main {
    public static void main(String args[]) throws InterruptedException {
        int n = 13;

        long l1 = System.currentTimeMillis();
        Queen queen = new Queen(n);
        queen.setQueen(0);
        long l2 = System.currentTimeMillis();
        System.out.println(queen.getResult() + " " + (l2 - l1) + " мс");

        l1 = System.currentTimeMillis();
        int count = NQueenWithThreads.calc(n);
        l2 = System.currentTimeMillis();
        System.out.println("Thread: " + count + " " + (l2 - l1) + " мс");


    }
}