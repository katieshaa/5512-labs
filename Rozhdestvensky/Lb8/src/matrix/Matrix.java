package matrix;

import java.lang.StringBuilder;

/**
 * Created by Vengr on 6.9.2017.
 */
public class Matrix {
    protected int[][] m;
    protected int len;
    protected int hei;

    public Matrix(int n, int nn) {
        m = new int[n][nn];
        len = n;
        hei = nn;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < hei; j++) {
                m[i][j] = 0;
            }
        }
    }

    public Matrix sum(Matrix k){

        if (len != k.len || hei != k.hei) {
            throw new RuntimeException("ERROR! in method Matrix sum -- Wrong sizes of matrices!");
        }

        Matrix n = new Matrix(len, hei);
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < hei; j++) {
                n.setElement(i, j, getElement(i, j) + k.getElement(i, j));
            }
        }
        return n;
    }

    public Matrix product(Matrix k){

        if (hei != k.len) {
            throw new RuntimeException("ERROR! in method Matrix product -- Wrong sizes of matrices!");
        }

        int index = 0;
        int tmp = 0;
        Matrix n = new Matrix(len, k.hei);
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < k.hei; j++) {
                while (index != k.len) {
                    tmp += getElement(i, index) * k.getElement(index, j);
                    index++;
                }
                n.setElement(i, j, tmp);
                index = 0;
                tmp = 0;
            }
        }
        return n;
    }

    public int setElement(int row, int column, int val){
        if (row >= len || column >= hei || row < 0 || column < 0) {
            throw new RuntimeException("ERROR! in method Matrix setElement -- out of range");
        }
        m[row][column] = val;
        return m[row][column];
    }

    public int getElement(int row, int column){
        if (row >= len || column >= hei || row < 0 || column < 0) {
            throw new RuntimeException("ERROR! in method Matrix getElement -- out of range");
        }
        return m[row][column];
    }

    public int getLen()
    {
        return len;
    }

    public int getHei()
    {
        return hei;
    }

    @Override
    public String toString() {

        StringBuilder str = new StringBuilder();
        for (int i = 0; i < len; i++) {
            str.append("[ ");
            for (int j = 0; j < hei; j++)
            {
                str.append(getElement(i, j));
                str.append(' ');
            }
            str.append(']');
            str.append('\n');
        }
        str.append('\n');
        return str.toString();
    }


    public boolean equals(Object kek) {

        if (!(kek instanceof Matrix)) {
            return false;
        }
        Matrix k = new Matrix(1, 1);
        k = (Matrix) kek;
        if (len == k.len && hei == k.hei) {
            for (int i = 0; i < m.length; i++) {
                for (int j = 0; j < m[0].length; j++) {
                    if (m[i][j] != k.m[i][j]) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }


}