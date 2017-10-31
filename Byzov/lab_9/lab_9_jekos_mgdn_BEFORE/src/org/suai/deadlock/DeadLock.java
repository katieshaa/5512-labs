package org.suai.deadlock;


import org.suai.matrix.UsualMatrix;
import org.suai.matrix.CellsProduct;


public class DeadLock {

    static final int countThreads = 2;				// не меняем константу

    /**
     *	Производит умножение матриц таким образом, чтобы возникла ситуация с DeadLock.
     *	В классе CellsProduct используются модификаторы synchronized!
     */
    public static void deadLockProductMatrix(UsualMatrix firstMatrix, UsualMatrix secondMatrix) throws InterruptedException {
        if(firstMatrix.getColumns() != secondMatrix.getRows()) {
            throw new RuntimeException("Incorrect matrix for product!");
        }

        UsualMatrix resultFirst = new UsualMatrix(firstMatrix.getRows(), secondMatrix.getColumns());
        int firstCountsElements = resultFirst.getRows() * resultFirst.getColumns();

        UsualMatrix resultSecond = new UsualMatrix(secondMatrix.getRows(), firstMatrix.getColumns());
        int secondCountsElements = resultSecond.getRows() * resultSecond.getColumns();

        CellsProduct[] threads = new CellsProduct[DeadLock.countThreads];

        threads[0] =  new CellsProduct(firstMatrix, secondMatrix, resultFirst, 0, firstCountsElements);
        threads[0].start();

        threads[1] =  new CellsProduct(secondMatrix, firstMatrix, resultSecond, 0, secondCountsElements);
        threads[1].start();

        for(int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
    }

}
