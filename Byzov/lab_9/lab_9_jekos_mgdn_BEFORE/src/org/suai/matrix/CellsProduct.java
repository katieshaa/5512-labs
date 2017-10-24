package org.suai.matrix;


import java.lang.Thread;

import java.util.Objects;


public class CellsProduct extends Thread {

    UsualMatrix firstMatrix;
    UsualMatrix secondMatrix;
    UsualMatrix result;
    int beginIndex;
    int endIndex;


    public CellsProduct(UsualMatrix firstMatrix, UsualMatrix secondMatrix, UsualMatrix result,
                        int beginIndex, int endIndex) {
        this.firstMatrix = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.result = result;
        this.beginIndex = beginIndex;
        this.endIndex = endIndex;
    }


    @Override
    public void run() {
        if(this.firstMatrix.getColumns() != this.secondMatrix.getRows()) {
            throw new RuntimeException("In thread incorrect matrix!");
        }

        if(this.result.getRows() != this.firstMatrix.getRows() || this.result.getColumns() != this.secondMatrix.getColumns()) {
            throw new RuntimeException("In thread incorrect matrix");
        }

        System.out.println("Wait first block synchronized! First matrix hash = " + this.firstMatrix.hashCode() +
                ". Second matrix hash = " + this.secondMatrix.hashCode() + ".");

        synchronized(this.firstMatrix) {
            try {
                sleep(4000);					//	ждем 4 секунды для того, чтобы второй поток успел запуститься
                //	и активировать deadlock
            }
            catch(InterruptedException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("Wait second block synchronized! First matrix hash = " + this.firstMatrix.hashCode() +
                    ". Second matrix hash = " + this.secondMatrix.hashCode() + ".");

            synchronized(this.secondMatrix) {
                System.out.println("We are in!");

                int lastIndex = this.result.getRows() * this.result.getColumns();
                for(int index = beginIndex; (index < endIndex) && (index < lastIndex); index++) {
                    int i = index / this.result.getColumns();
                    int j = index % this.result.getColumns();

                    this.result.setElement(i, j, 0);

                    for(int k = 0; k < this.firstMatrix.getColumns(); k++) {
                        this.result.setElement(i, j, this.result.getElement(i, j)
                                + (this.firstMatrix.getElement(i, k) * this.secondMatrix.getElement(k, j)));
                    }
                }
            }
        }
    }

}