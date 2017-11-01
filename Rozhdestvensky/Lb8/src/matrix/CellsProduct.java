package matrix;

import java.lang.Thread;

/**
 * Created by Vengr on 6.9.2017.
 */


public class CellsProduct extends Thread {
    Matrix matr1;
    Matrix matr2;
    Matrix solve;
    int startInd;
    int endInd;


    public CellsProduct(Matrix matrix1, Matrix matrix2, Matrix result,
                        int start, int end) {
        matr1 = matrix1;
        matr2 = matrix2;
        solve = result;
        startInd = start;
        endInd = end;
    }


    @Override
    public void run() {
        if(matr1.getHei() != matr2.getLen()) {
            throw new RuntimeException("In thread incorrect matrix!");
        }

        if(solve.getLen() != matr1.getLen() || solve.getHei() != matr2.getHei()) {
            throw new RuntimeException("In thread incorrect matrix");
        }

        for(int i = startInd; (i < endInd) && (i < solve.getLen() * solve.getHei()); i++) {
            int row = i / solve.getHei();
            int column = i % solve.getHei();
            solve.setElement(row, column, 0);

            for(int j = 0; j < matr1.getHei(); j++) {
                solve.setElement(row, column, solve.getElement(row, column) + (matr1.getElement(row, j) * matr2.getElement(j, column)));
            }

        }
    }
}
