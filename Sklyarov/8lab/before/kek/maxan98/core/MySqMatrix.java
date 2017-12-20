package kek.maxan98.core;

/**
 * Created by MaksSklyarov on 23.02.17.
 */

import kek.maxan98.core.exc.MyException;
public class MySqMatrix extends MyMatrix {

    public MySqMatrix(int row) {
        super(row, row);
        for(int i = 0; i < getrows();i++)
            this.setData(i,i,1);
    }
    public MySqMatrix mul(MySqMatrix m2) throws MyException {
        if(this.getcols() != m2.getrows()){
            throw new MyException(69, "Братан, такие матрицы я не умножу.");

        }

        int m = this.getData().length;
        int n = m2.getData()[0].length;
        int o = m2.getData().length;
        MySqMatrix res = new MySqMatrix(n);
        res.zero();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < o; k++) {
                    res.getData()[i][j] += this.getData()[i][k] * m2.getData()[k][j];
                }
            }
        }
        return res;
    }
}
